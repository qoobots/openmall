package com.qoobot.openmall.merchant.service.impl;

import com.qoobot.openmall.common.domain.entity.OrderItem;
import com.qoobot.openmall.common.domain.entity.OrderMaster;
import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.common.domain.entity.ProductSpu;
import com.qoobot.openmall.merchant.dto.OrderItemVO;
import com.qoobot.openmall.merchant.dto.OrderVO;
import com.qoobot.openmall.merchant.dto.PageResult;
import com.qoobot.openmall.merchant.repository.OrderItemRepository;
import com.qoobot.openmall.merchant.repository.OrderRepository;
import com.qoobot.openmall.merchant.repository.ProductSkuRepository;
import com.qoobot.openmall.merchant.repository.ProductSpuRepository;
import com.qoobot.openmall.merchant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单Service实现
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductSkuRepository productSkuRepository;
    private final ProductSpuRepository productSpuRepository;

    @Override
    public PageResult<OrderVO> queryPage(Integer status, String keyword, Pageable pageable, Long shopId) {
        // 构建查询条件
        Specification<OrderMaster> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 店铺ID
            predicates.add(criteriaBuilder.equal(root.get("shopId"), shopId));

            // 状态筛选
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderStatus"), status));
            }

            // 关键字搜索
            if (StringUtils.hasText(keyword)) {
                String likeKeyword = "%" + keyword + "%";
                predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(root.get("orderNo"), likeKeyword)
                    // TODO: 可以添加更多搜索条件，如买家账号等
                ));
            }

            // 未删除
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 分页查询
        Page<OrderMaster> page = orderRepository.findAll(spec, pageable);

        // 转换为VO
        List<OrderVO> list = page.getContent().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(list, page.getTotalElements(),
                pageable.getPageNumber() + 1, pageable.getPageSize());
    }

    @Override
    public OrderVO getById(Long id, Long shopId) {
        OrderMaster order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        // 验证店铺权限
        if (!order.getShopId().equals(shopId)) {
            throw new RuntimeException("无权访问该订单");
        }

        return convertToVO(order);
    }

    @Override
    public OrderVO getByOrderNo(String orderNo, Long shopId) {
        OrderMaster order = orderRepository.findByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 验证店铺权限
        if (!order.getShopId().equals(shopId)) {
            throw new RuntimeException("无权访问该订单");
        }

        return convertToVO(order);
    }

    @Transactional
    @Override
    public void ship(Long orderId, String logisticsCompany, String logisticsNo, Long shopId) {
        OrderMaster order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        // 验证店铺权限
        if (!order.getShopId().equals(shopId)) {
            throw new RuntimeException("无权操作该订单");
        }

        // 验证订单状态（只有待发货的订单才能发货）
        if (order.getOrderStatus() != 2) {
            throw new RuntimeException("订单状态不正确，无法发货");
        }

        // 更新订单状态
        order.setOrderStatus(3); // 已发货
        order.setLogisticsCompany(logisticsCompany);
        order.setLogisticsNo(logisticsNo);
        order.setShipTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setUpdateBy(shopId); // TODO: 修改为当前用户ID

        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void batchShip(java.util.List<Long> orderIds, String logisticsCompany, String logisticsNo, Long shopId) {
        for (Long orderId : orderIds) {
            ship(orderId, logisticsCompany, logisticsNo, shopId);
        }
    }

    /**
     * 转换为VO
     */
    private OrderVO convertToVO(OrderMaster order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);

        // 设置状态文本和徽章样式
        vo.setStatusText(getOrderStatusText(order.getOrderStatus()));
        vo.setStatusBadge(getOrderStatusBadge(order.getOrderStatus()));

        // 查询订单明细
        List<OrderItem> items = orderItemRepository.findByOrderNo(order.getOrderNo());
        List<OrderItemVO> itemVOList = items.stream().map(item -> {
            OrderItemVO itemVO = new OrderItemVO();
            BeanUtils.copyProperties(item, itemVO);
            
            // 设置商品主图
            try {
                ProductSku productSku = productSkuRepository.findById(item.getSkuId()).orElse(null);
                if (productSku != null) {
                    ProductSpu productSpu = productSpuRepository.findById(productSku.getSpuId()).orElse(null);
                    if (productSpu != null) {
                        itemVO.setMainImage(productSpu.getMainImage());
                    }
                }
            } catch (Exception e) {
                // 如果获取商品图片失败，忽略异常
                itemVO.setMainImage("/images/default-product.jpg");
            }
            
            return itemVO;
        }).collect(Collectors.toList());

        vo.setItems(itemVOList);

        // 计算商品总数
        int totalQuantity = itemVOList.stream()
                .mapToInt(OrderItemVO::getQuantity)
                .sum();
        vo.setTotalQuantity(totalQuantity);

        return vo;
    }

    /**
     * 获取订单状态文本
     */
    private String getOrderStatusText(Integer status) {
        switch (status) {
            case 1: return "待付款";
            case 2: return "待发货";
            case 3: return "待收货";
            case 4: return "已完成";
            case 5: return "已取消";
            case 6: return "售后中";
            default: return "未知";
        }
    }

    /**
     * 获取订单状态徽章样式
     */
    private String getOrderStatusBadge(Integer status) {
        switch (status) {
            case 1: return "bg-warning";
            case 2: return "bg-info";
            case 3: return "bg-primary";
            case 4: return "bg-success";
            case 5: return "bg-secondary";
            case 6: return "bg-danger";
            default: return "bg-secondary";
        }
    }
}
