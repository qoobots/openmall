package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细VO
 */
@Data
public class OrderItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderNo;
    private Long skuId;
    private String productName;
    private String mainImage;
    private String specs;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal amount;
}
