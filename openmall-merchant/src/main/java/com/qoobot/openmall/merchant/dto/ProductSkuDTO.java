package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 商品SKU DTO
 */
@Data
public class ProductSkuDTO {
    private Long id;
    private String skuName;
    private String specs;
    private BigDecimal price;
    private Integer stock;
    private String skuCode;
}
