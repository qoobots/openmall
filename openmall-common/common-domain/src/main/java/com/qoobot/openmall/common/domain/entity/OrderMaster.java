package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单主表实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_master")
public class OrderMaster extends BaseEntity {

    @Column(name = "order_no", nullable = false, unique = true, length = 32)
    private String orderNo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "order_status", nullable = false)
    private Integer orderStatus;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "freight_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal freightAmount;

    @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "pay_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal payAmount;

    @Column(name = "pay_type")
    private Integer payType;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "consignee_name", nullable = false, length = 50)
    private String consigneeName;

    @Column(name = "consignee_phone", nullable = false, length = 20)
    private String consigneePhone;

    @Column(name = "consignee_address", nullable = false, length = 500)
    private String consigneeAddress;

    @Column(name = "logistics_company", length = 100)
    private String logisticsCompany;

    @Column(name = "logistics_no", length = 100)
    private String logisticsNo;

    @Column(name = "ship_time")
    private LocalDateTime shipTime;

    @Column(name = "receive_time")
    private LocalDateTime receiveTime;

    @Column(name = "remark", length = 500)
    private String remark;
}
