package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单确认VO
 */
@Data
public class OrderConfirmVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收货地址列表
     */
    private List<UserAddressVO> addresses;

    /**
     * 默认收货地址
     */
    private UserAddressVO defaultAddress;

    /**
     * 订单商品列表
     */
    private List<OrderItemVO> items;

    /**
     * 商品总额
     */
    private BigDecimal totalAmount;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 应付金额
     */
    private BigDecimal payAmount;
}
