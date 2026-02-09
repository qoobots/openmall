package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息VO
 */
@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String mobile;
    private String email;
    private Integer userType;
    private String userTypeDesc;
}
