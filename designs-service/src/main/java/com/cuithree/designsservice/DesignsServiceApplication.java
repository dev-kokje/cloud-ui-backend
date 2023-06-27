package com.cuithree.designsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DesignsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignsServiceApplication.class, args);
    }

}
