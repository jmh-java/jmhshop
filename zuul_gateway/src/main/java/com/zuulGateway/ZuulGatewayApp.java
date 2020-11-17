package com.zuulGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableZuulProxy  // 开启路由代理（服务器网关）
@EnableEurekaClient
//启动项
public class ZuulGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApp.class, args);
    }
}
