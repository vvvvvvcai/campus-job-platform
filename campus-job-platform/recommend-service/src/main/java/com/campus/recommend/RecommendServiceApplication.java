package com.campus.recommend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 推荐服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.campus.recommend", "com.campus.common"})
@EnableDiscoveryClient
@EnableFeignClients
public class RecommendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendServiceApplication.class, args);
        System.out.println("========== 推荐服务启动成功 ==========");
    }
}
