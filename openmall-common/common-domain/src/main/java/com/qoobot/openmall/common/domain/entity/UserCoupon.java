package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户优惠券实体（用户领取的优惠券记录）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_coupon")
public class UserCoupon extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "use_time")
    private LocalDateTime useTime;

    @Column(name = "order_no", length = 32)
    private String orderNo;
}
