package com.qoobot.openmall.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 平台管理启动类
 */
@SpringBootApplication(scanBasePackages = "com.qoobot.openmall")
public class PlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}
