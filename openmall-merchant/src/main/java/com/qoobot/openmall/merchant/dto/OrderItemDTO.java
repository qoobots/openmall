package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 订单明细DTO
 */
@Data
public class OrderItemDTO {
    private Long skuId;
    private String productName;
    private String specs;
    private BigDecimal price;
    private Integer quantity;
}
