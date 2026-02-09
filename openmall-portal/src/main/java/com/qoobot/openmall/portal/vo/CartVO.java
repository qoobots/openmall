package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车VO
 */
@Data
public class CartVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long skuId;
    private String productName;
    private String mainImage;
    private String specs;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal amount;
    private Integer stock;
    private Integer isSelected;
}
