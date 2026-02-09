package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单VO
 */
@Data
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderNo;
    private Integer orderStatus;
    private String orderStatusDesc;
    private BigDecimal totalAmount;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Integer payType;
    private String payTypeDesc;
    private LocalDateTime payTime;
    private LocalDateTime createTime;
    private Integer itemCount;

    /**
     * 订单商品列表
     */
    private List<OrderItemVO> items;
}
