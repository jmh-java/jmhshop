package com.provider.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient   // 生产者服务启动后会自动注册到eureka服务中
@EnableCircuitBreaker // 对hystrix熔断机制的支持
public class ProviderCustomerApp {
    public static void main(String[] args) {
        SpringApplication.run(ProviderCustomerApp.class,
                args);
    }
}
