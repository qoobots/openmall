package com.qoobot.openmall.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用户注册DTO
 */
@Data
public class UserRegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名必须是4-20位字母、数字或下划线")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^.{6,20}$", message = "密码长度必须在6-20位之间")
    private String password;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "邮箱格式不正确")
    private String email;

    private String nickname;
}
