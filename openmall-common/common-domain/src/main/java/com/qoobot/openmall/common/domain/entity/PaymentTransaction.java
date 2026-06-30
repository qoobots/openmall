package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction extends BaseEntity {

    @Column(name = "order_no", nullable = false, length = 32)
    private String orderNo;

    @Column(name = "pay_no", nullable = false, unique = true, length = 64)
    private String payNo;

    @Column(name = "pay_type", nullable = false)
    private Integer payType;

    @Column(name = "pay_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal payAmount;

    @Column(name = "pay_status", nullable = false)
    private Integer payStatus;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "out_trade_no", length = 64)
    private String outTradeNo;

    @Column(name = "buyer_name", length = 100)
    private String buyerName;

    @Column(name = "notify_data", columnDefinition = "TEXT")
    private String notifyData;
}
