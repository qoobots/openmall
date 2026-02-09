package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.OrderItem;
import com.qoobot.openmall.common.domain.entity.OrderMaster;
import com.qoobot.openmall.common.domain.entity.ProductSku;
import com.qoobot.openmall.common.domain.entity.UserAddress;
import com.qoobot.openmall.portal.dto.OrderSubmitDTO;
import com.qoobot.openmall.portal.repository.OrderItemRepository;
import com.qoobot.openmall.portal.repository.OrderMasterRepository;
import com.qoobot.openmall.portal.repository.ProductSkuRepository;
import com.qoobot.openmall.portal.repository.UserAddressRepository;
import com.qoobot.openmall.portal.service.OrderService;
import com.qoobot.openmall.portal.vo.OrderConfirmVO;
import com.qoobot.openmall.portal.vo.OrderDetailVO;
import com.qoobot.openmall.portal.vo.OrderItemVO;
import com.qoobot.openmall.portal.vo.OrderVO;
import com.qoobot.openmall.portal.vo.UserAddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订单服务实现
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMasterRepository orderMasterRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductSkuRepository productSkuRepository;
    private final UserAddressRepository userAddressRepository;

    @Override
    public OrderConfirmVO getConfirmVO(Long userId, Long skuId, Integer quantity) {
        OrderConfirmVO confirmVO = new OrderConfirmVO();

        // 查询收货地址
        List<UserAddress> addresses = userAddressRepository.findByUserIdAndIsDeletedOrderByIsDefaultDescIdDesc(userId, 0);
        confirmVO.setAddresses(convertToAddressVOList(addresses));

        // 设置默认地址
        UserAddress defaultAddress = addresses.stream()
                .filter(addr -> addr.getIsDefault() == 1)
                .findFirst()
                .orElse(null);
        confirmVO.setDefaultAddress(convertToAddressVO(defaultAddress));

        // 查询商品信息
        ProductSku sku = productSkuRepository.findById(skuId).orElse(null);
        if (sku != null) {
            List<OrderItemVO> items = new ArrayList<>();
            OrderItemVO item = new OrderItemVO();
            item.setSkuId(sku.getId());
            item.setProductName("商品-" + sku.getId());
            item.setSpecs(sku.getSpecs());
            item.setPrice(sku.getPrice());
            item.setQuantity(quantity);
            item.setAmount(sku.getPrice().multiply(new BigDecimal(quantity)));
            items.add(item);

            confirmVO.setItems(items);
            confirmVO.setTotalAmount(item.getAmount());
            confirmVO.setFreightAmount(BigDecimal.ZERO);
            confirmVO.setDiscountAmount(BigDecimal.ZERO);
            confirmVO.setPayAmount(item.getAmount());
        }

        return confirmVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String submitOrder(OrderSubmitDTO dto) {
        // 生成订单号
        String orderNo = generateOrderNo();

        // 创建订单主表
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderNo(orderNo);
        orderMaster.setUserId(dto.getUserId());
        orderMaster.setShopId(1L); // TODO: 从SKU获取店铺ID
        orderMaster.setOrderStatus(0); // 待付款
        orderMaster.setTotalAmount(BigDecimal.ZERO);
        orderMaster.setFreightAmount(BigDecimal.ZERO);
        orderMaster.setDiscountAmount(BigDecimal.ZERO);
        orderMaster.setPayAmount(BigDecimal.ZERO);
        orderMaster.setConsigneeName("测试收货人");
        orderMaster.setConsigneePhone("13800138000");
        orderMaster.setConsigneeAddress("测试地址");
        orderMaster.setCreateBy(dto.getUserId());
        orderMaster.setUpdateBy(dto.getUserId());

        // TODO: 从购物车或商品列表获取商品信息
        orderMaster.setTotalAmount(new BigDecimal("99.00"));
        orderMaster.setPayAmount(new BigDecimal("99.00"));

        orderMasterRepository.save(orderMaster);

        // 创建订单明细
        // TODO: 根据skuIds查询商品并创建订单明细

        return orderNo;
    }

    @Override
    public OrderDetailVO getOrderByNo(String orderNo) {
        OrderMaster order = orderMasterRepository.findByOrderNo(orderNo).orElse(null);
        if (order == null) {
            return null;
        }
        return convertToDetailVO(order);
    }

    @Override
    public OrderDetailVO getOrderDetail(Long id) {
        OrderMaster order = orderMasterRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        return convertToDetailVO(order);
    }

    @Override
    public PageResult<OrderVO> getUserOrders(Long userId, Integer status, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 20);
        Page<OrderMaster> page;

        if (status == null) {
            page = orderMasterRepository.findByUserIdAndIsDeletedOrderByCreateTimeDesc(userId, 0, pageable);
        } else {
            page = orderMasterRepository.findByUserIdAndOrderStatusAndIsDeletedOrderByCreateTimeDesc(userId, status, 0, pageable);
        }

        List<OrderVO> list = page.getContent().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(list, page.getTotalElements(), pageNum, 20);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long id, String reason) {
        OrderMaster order = orderMasterRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(4); // 已取消
            orderMasterRepository.save(order);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReceipt(Long id) {
        OrderMaster order = orderMasterRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(3); // 已完成
            orderMasterRepository.save(order);
        }
    }

    @Override
    public void deleteOrder(Long userId, String orderNo) {
        // TODO: 实现删除订单逻辑
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderVO convertToVO(OrderMaster order) {
        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setOrderStatusDesc(getOrderStatusDesc(order.getOrderStatus()));
        vo.setTotalAmount(order.getTotalAmount());
        vo.setFreightAmount(order.getFreightAmount());
        vo.setDiscountAmount(order.getDiscountAmount());
        vo.setPayAmount(order.getPayAmount());
        vo.setPayTime(order.getPayTime());
        vo.setCreateTime(order.getCreateTime());
        return vo;
    }

    private OrderDetailVO convertToDetailVO(OrderMaster order) {
        OrderDetailVO vo = new OrderDetailVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setOrderStatusDesc(getOrderStatusDesc(order.getOrderStatus()));
        vo.setTotalAmount(order.getTotalAmount());
        vo.setFreightAmount(order.getFreightAmount());
        vo.setDiscountAmount(order.getDiscountAmount());
        vo.setPayAmount(order.getPayAmount());
        vo.setPayTime(order.getPayTime());
        vo.setConsigneeName(order.getConsigneeName());
        vo.setConsigneePhone(order.getConsigneePhone());
        vo.setConsigneeAddress(order.getConsigneeAddress());
        vo.setCreateTime(order.getCreateTime());
        vo.setUpdateTime(order.getUpdateTime());
        return vo;
    }

    private UserAddressVO convertToAddressVO(UserAddress address) {
        if (address == null) {
            return null;
        }
        UserAddressVO vo = new UserAddressVO();
        vo.setId(address.getId());
        vo.setConsigneeName(address.getConsigneeName());
        vo.setConsigneePhone(address.getConsigneePhone());
        vo.setProvince(address.getProvince());
        vo.setCity(address.getCity());
        vo.setDistrict(address.getDistrict());
        vo.setDetailAddress(address.getDetailAddress());
        vo.setFullAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        vo.setIsDefault(address.getIsDefault());
        return vo;
    }

    private List<UserAddressVO> convertToAddressVOList(List<UserAddress> addresses) {
        return addresses.stream()
                .map(this::convertToAddressVO)
                .collect(Collectors.toList());
    }

    private String getOrderStatusDesc(Integer status) {
        if (status == null) return "未知";
        return switch (status) {
            case 0 -> "待付款";
            case 1 -> "待发货";
            case 2 -> "已发货";
            case 3 -> "已完成";
            case 4 -> "已取消";
            case 5 -> "退款中";
            case 6 -> "已退款";
            default -> "未知";
        };
    }
}
