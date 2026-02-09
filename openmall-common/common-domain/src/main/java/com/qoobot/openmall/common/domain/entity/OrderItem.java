package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单明细实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Column(name = "order_no", nullable = false, length = 32)
    private String orderNo;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "specs", columnDefinition = "TEXT")
    private String specs;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
}
