package com.example.opctbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.opctbatch", "com.example.opct_notice"})
@EnableEurekaClient
@EnableFeignClients
public class OpctBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpctBatchApplication.class, args);
    }

}
