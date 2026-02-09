package com.qoobot.openmall.common.core.enums;

import lombok.Getter;

/**
 * 店铺类型枚举
 */
@Getter
public enum ShopType {
    FLAGSHIP(1, "旗舰店"),
    SPECIALTY(2, "专卖店"),
   专营店(3, "专营店");

    private final Integer code;
    private final String desc;

    ShopType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
