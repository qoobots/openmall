package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "coupon")
public class Coupon extends BaseEntity {

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "coupon_name", nullable = false, length = 100)
    private String couponName;

    @Column(name = "coupon_type", nullable = false)
    private Integer couponType;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "min_consume", precision = 10, scale = 2, nullable = false)
    private BigDecimal minConsume;

    @Column(name = "total_count", nullable = false)
    private Integer totalCount;

    @Column(name = "remain_count", nullable = false)
    private Integer remainCount;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "use_scope", length = 500)
    private String useScope;

    @Column(name = "remark", length = 500)
    private String remark;

    @Column(name = "status", nullable = false)
    private Integer status;
}
