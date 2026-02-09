package com.qoobot.openmall.common.core.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
public enum OrderStatus {
    PENDING_PAYMENT(0, "待付款"),
    PENDING_SHIPMENT(1, "待发货"),
    SHIPPED(2, "已发货"),
    COMPLETED(3, "已完成"),
    CANCELLED(4, "已取消"),
    REFUNDING(5, "退款中"),
    REFUNDED(6, "已退款");

    private final Integer code;
    private final String desc;

    OrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatus fromCode(Integer code) {
        if (code == null) return null;
        for (OrderStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
