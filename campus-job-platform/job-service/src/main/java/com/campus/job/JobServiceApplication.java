package com.campus.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 职位服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.campus.job", "com.campus.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class JobServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class, args);
        System.out.println("========== 职位服务启动成功 ==========");
    }
}
