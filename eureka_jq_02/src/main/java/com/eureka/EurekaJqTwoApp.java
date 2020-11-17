package com.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer   // EurekaServer服务器端启动类， 接受其他微服务注册
//启动项
public class EurekaJqTwoApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaJqTwoApp.class, args);
    }
}
