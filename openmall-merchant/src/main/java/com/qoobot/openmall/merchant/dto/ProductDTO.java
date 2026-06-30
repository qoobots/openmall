package com.qoobot.openmall.merchant.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品DTO
 */
@Data
public class ProductDTO {
    private Long id;
    private Long categoryId;
    private Long brandId;
    private String productName;
    private String productSubTitle;
    private String productDesc;
    private String mainImage;
    private List<String> albumImages;
    private String productCode;
    private String barCode;
    private BigDecimal weight;
    private Long freightTemplateId;
    private String tags;
    private Integer isNew;
    private Integer isHot;
    private Integer isRecommend;
    private Integer sortOrder;
    private List<ProductSkuDTO> skuList;
}
