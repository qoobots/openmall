package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券VO
 */
@Data
public class CouponVO {
    private Long id;
    private String couponName;
    private Integer couponType;
    private String couponTypeText;
    private BigDecimal discountAmount;
    private BigDecimal minConsume;
    private Integer totalCount;
    private Integer remainCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private String statusText;
    private Integer receivedCount;
    private Integer usedCount;
}
