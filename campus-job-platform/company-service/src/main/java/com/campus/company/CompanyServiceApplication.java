package com.campus.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 企业服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.campus.company", "com.campus.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class CompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
        System.out.println("========== 企业服务启动成功 ==========");
    }
}
