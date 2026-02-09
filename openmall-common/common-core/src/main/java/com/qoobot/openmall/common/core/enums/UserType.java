package com.qoobot.openmall.common.core.enums;

import lombok.Getter;

/**
 * 用户类型枚举
 */
@Getter
public enum UserType {
    BUYER(1, "买家"),
    MERCHANT(2, "商家"),
    PLATFORM_ADMIN(3, "平台管理员");

    private final Integer code;
    private final String desc;

    UserType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserType fromCode(Integer code) {
        if (code == null) return null;
        for (UserType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
