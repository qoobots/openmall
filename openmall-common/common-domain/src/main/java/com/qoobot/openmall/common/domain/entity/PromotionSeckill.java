package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 秒杀活动实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "promotion_seckill")
public class PromotionSeckill extends BaseEntity {

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "promotion_name", nullable = false, length = 100)
    private String promotionName;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "seckill_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal seckillPrice;

    @Column(name = "seckill_stock", nullable = false)
    private Integer seckillStock;

    @Column(name = "sold_count", nullable = false)
    private Integer soldCount = 0;

    @Column(name = "limit_count", nullable = false)
    private Integer limitCount = 1;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false)
    private Integer status = 0;
}
