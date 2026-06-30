package com.qoobot.openmall.merchant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 认证控制器
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}