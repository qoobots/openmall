package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券DTO
 */
@Data
public class CouponDTO {
    private Long id;
    private String couponName;
    private Integer couponType;
    private BigDecimal discountAmount;
    private BigDecimal minConsume;
    private Integer totalCount;
    private Integer remainCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String useScope;
}
