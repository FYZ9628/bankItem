package com.example.opct_notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(scanBasePackages = {"com.example.base", "com.example.opct_notice", "com.example.opct_base"})
@SpringBootApplication
@MapperScan({"com.example.opct_notice.base.**.mapper"})
@ComponentScan({"com.example.opct_notice","com.example.opct_base", "com.example.base"})
@EnableEurekaClient
@EnableFeignClients
public class OpctNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpctNoticeApplication.class, args);
    }

}
