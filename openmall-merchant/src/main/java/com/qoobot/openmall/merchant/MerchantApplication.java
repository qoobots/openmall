package com.qoobot.openmall.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 商家后台启动类
 */
@SpringBootApplication(scanBasePackages = "com.qoobot.openmall")
@EntityScan(basePackages = "com.qoobot.openmall.common.domain.entity")
public class MerchantApplication {
    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class, args);
    }
}
