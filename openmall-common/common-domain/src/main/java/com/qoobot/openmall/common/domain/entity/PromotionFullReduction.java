package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 满减活动实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "promotion_full_reduction")
public class PromotionFullReduction extends BaseEntity {

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "promotion_name", nullable = false, length = 100)
    private String promotionName;

    @Column(name = "full_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal fullAmount;

    @Column(name = "reduce_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal reduceAmount;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Column(name = "product_ids", columnDefinition = "TEXT")
    private String productIds;
}
