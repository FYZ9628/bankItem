package com.example.blctonline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = {"com.example.opct_notice.base.**.mapper", "com.example.opctonline.**.mapper"})
@ComponentScan({"com.example.blctonline","com.example.opct_notice","com.example.opct_base", "com.example.base"})
public class BlctOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlctOnlineApplication.class, args);
    }

}
