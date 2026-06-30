package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品VO
 */
@Data
public class ProductVO {
    private Long id;
    private Long categoryId;
    private Long brandId;
    private String productName;
    private String productSubTitle;
    private String mainImage;
    private List<String> albumImages;
    private Boolean isShelves;
    private Integer sales;
    private Integer views;
    private Integer totalStock;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer skuCount;
    private LocalDateTime createTime;
    private String categoryName;
    private String brandName;
}
