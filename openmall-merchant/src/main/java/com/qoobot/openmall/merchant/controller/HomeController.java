package com.qoobot.openmall.merchant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商户后台首页控制器
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * 商户后台首页
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "dashboard/index";
    }

    /**
     * 控制台页面
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "layout/main";
    }
    
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}