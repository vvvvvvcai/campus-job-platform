package com.campus.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 投递服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.campus.application", "com.campus.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class ApplicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServiceApplication.class, args);
        System.out.println("========== 投递服务启动成功 ==========");
    }
}
