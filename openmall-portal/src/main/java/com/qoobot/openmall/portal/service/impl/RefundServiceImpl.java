package com.qoobot.openmall.portal.service.impl;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.OrderMaster;
import com.qoobot.openmall.common.domain.entity.RefundOrder;
import com.qoobot.openmall.portal.repository.OrderMasterRepository;
import com.qoobot.openmall.portal.repository.RefundOrderRepository;
import com.qoobot.openmall.portal.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 退款服务实现
 */
@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundOrderRepository refundOrderRepository;
    private final OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public RefundOrder applyRefund(Long userId, String orderNo, String reason, Integer refundType) {
        // 查询订单
        OrderMaster order = orderMasterRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        // 验证订单状态（已支付/已发货才能退款）
        if (order.getOrderStatus() != 1 && order.getOrderStatus() != 2) {
            throw new RuntimeException("当前订单状态不允许退款");
        }

        // 检查是否已有退款记录
        if (refundOrderRepository.findByOrderNoAndIsDeleted(orderNo, 0).isPresent()) {
            throw new RuntimeException("该订单已有退款申请");
        }

        // 生成退款单号
        String refundNo = "RF" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        // 创建退款单
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setRefundNo(refundNo);
        refundOrder.setOrderNo(orderNo);
        refundOrder.setUserId(userId);
        refundOrder.setShopId(order.getShopId());
        refundOrder.setRefundAmount(order.getPayAmount());
        refundOrder.setRefundReason(reason);
        refundOrder.setRefundType(refundType != null ? refundType : 1); // 默认仅退款
        refundOrder.setRefundStatus(0); // 待审核
        refundOrder.setCreateTime(LocalDateTime.now());
        refundOrder.setUpdateTime(LocalDateTime.now());
        refundOrder.setCreateBy(userId);
        refundOrder.setUpdateBy(userId);

        // 更新订单状态为退款中
        order.setOrderStatus(5);
        orderMasterRepository.save(order);

        return refundOrderRepository.save(refundOrder);
    }

    @Override
    public PageResult<RefundOrder> listUserRefunds(Long userId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<RefundOrder> page = refundOrderRepository.findByUserIdAndIsDeletedOrderByCreateTimeDesc(userId, 0, pageable);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    public RefundOrder getRefundDetail(Long refundId) {
        return refundOrderRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("退款单不存在"));
    }

    @Override
    @Transactional
    public RefundOrder auditRefund(Long refundId, Integer auditResult, String refuseReason, Long shopId) {
        RefundOrder refund = refundOrderRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("退款单不存在"));

        if (refund.getRefundStatus() != 0) {
            throw new RuntimeException("退款单已处理");
        }

        if (auditResult == 1) {
            // 同意退款
            refund.setRefundStatus(3); // 退款中
            // 更新关联订单状态
            OrderMaster order = orderMasterRepository.findByOrderNo(refund.getOrderNo()).orElse(null);
            if (order != null) {
                order.setOrderStatus(6); // 已退款
                orderMasterRepository.save(order);
            }
        } else {
            // 拒绝退款
            refund.setRefundStatus(2);
            refund.setRefuseReason(refuseReason);
            // 恢复订单状态
            OrderMaster order = orderMasterRepository.findByOrderNo(refund.getOrderNo()).orElse(null);
            if (order != null) {
                order.setOrderStatus(order.getOrderStatus() == 5 ? 2 : order.getOrderStatus());
                orderMasterRepository.save(order);
            }
        }

        refund.setAuditTime(LocalDateTime.now());
        refund.setRefundTime(auditResult == 1 ? LocalDateTime.now() : null);
        refund.setUpdateTime(LocalDateTime.now());
        return refundOrderRepository.save(refund);
    }

    @Override
    public PageResult<RefundOrder> listShopRefunds(Long shopId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<RefundOrder> page = refundOrderRepository.findByShopIdAndIsDeletedOrderByCreateTimeDesc(shopId, 0, pageable);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    @Override
    @Transactional
    public RefundOrder arbitrateRefund(Long refundId, Integer result, String remark) {
        RefundOrder refund = refundOrderRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("退款单不存在"));

        if (result == 1) {
            refund.setRefundStatus(3); // 同意退款，退款中
            OrderMaster order = orderMasterRepository.findByOrderNo(refund.getOrderNo()).orElse(null);
            if (order != null) {
                order.setOrderStatus(6);
                orderMasterRepository.save(order);
            }
        } else {
            refund.setRefundStatus(2); // 驳回
            refund.setRefuseReason(remark);
        }

        refund.setAuditTime(LocalDateTime.now());
        refund.setUpdateTime(LocalDateTime.now());
        return refundOrderRepository.save(refund);
    }
}
