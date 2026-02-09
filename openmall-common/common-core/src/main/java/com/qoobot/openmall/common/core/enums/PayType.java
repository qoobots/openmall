package com.qoobot.openmall.common.core.enums;

import lombok.Getter;

/**
 * 支付方式枚举
 */
@Getter
public enum PayType {
    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信支付"),
    BALANCE(3, "余额支付");

    private final Integer code;
    private final String desc;

    PayType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
