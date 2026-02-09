package com.qoobot.openmall.portal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 订单提交DTO
 */
@Data
public class OrderSubmitDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收货地址ID
     */
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    /**
     * 购物车SKU ID列表
     */
    private List<Long> skuIds;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 单个SKU ID（直接购买时使用）
     */
    private Long skuId;

    /**
     * 单个商品数量（直接购买时使用）
     */
    private Integer quantity;
}
