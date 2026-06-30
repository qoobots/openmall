package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 订单明细VO
 */
@Data
public class OrderItemVO {
    private Long id;
    private String orderNo;
    private Long skuId;
    private Long spuId;
    private String productName;
    private String specs;
    private String mainImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal amount;
}
