package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款单实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "refund_order")
public class RefundOrder extends BaseEntity {

    @Column(name = "order_no", nullable = false, length = 32)
    private String orderNo;

    @Column(name = "refund_no", nullable = false, unique = true, length = 32)
    private String refundNo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "refund_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "refund_reason", length = 500)
    private String refundReason;

    @Column(name = "refund_type", nullable = false)
    private Integer refundType;

    @Column(name = "refund_status", nullable = false)
    private Integer refundStatus;

    @Column(name = "refuse_reason", length = 500)
    private String refuseReason;

    @Column(name = "audit_time")
    private LocalDateTime auditTime;

    @Column(name = "refund_time")
    private LocalDateTime refundTime;
}
