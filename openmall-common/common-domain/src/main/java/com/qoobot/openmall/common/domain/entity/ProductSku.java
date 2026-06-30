package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品SKU实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_sku")
public class ProductSku extends BaseEntity {

    @Column(name = "spu_id", nullable = false)
    private Long spuId;

    @Column(name = "sku_name", length = 200)
    private String skuName;

    @Column(name = "specs", columnDefinition = "TEXT")
    private String specs;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "stock_warning")
    private Integer stockWarning = 0;

    @Column(name = "sales", nullable = false)
    private Integer sales = 0;
}
