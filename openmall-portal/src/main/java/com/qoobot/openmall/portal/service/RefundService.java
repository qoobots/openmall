package com.qoobot.openmall.portal.service;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.domain.entity.RefundOrder;

/**
 * 退款服务接口
 */
public interface RefundService {

    /**
     * 买家申请退款
     */
    RefundOrder applyRefund(Long userId, String orderNo, String reason, Integer refundType);

    /**
     * 买家退款列表
     */
    PageResult<RefundOrder> listUserRefunds(Long userId, int pageNum, int pageSize);

    /**
     * 退款详情
     */
    RefundOrder getRefundDetail(Long refundId);

    /**
     * 商家审核退款（同意/拒绝）
     */
    RefundOrder auditRefund(Long refundId, Integer auditResult, String refuseReason, Long shopId);

    /**
     * 商家退款列表
     */
    PageResult<RefundOrder> listShopRefunds(Long shopId, int pageNum, int pageSize);

    /**
     * 平台仲裁退款
     */
    RefundOrder arbitrateRefund(Long refundId, Integer result, String remark);
}
