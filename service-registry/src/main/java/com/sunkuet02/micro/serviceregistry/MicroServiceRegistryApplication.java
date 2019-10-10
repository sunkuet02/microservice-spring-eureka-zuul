package com.sunkuet02.micro.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroServiceRegistryApplication {
    public static void main(String [] args) {
        SpringApplication.run(MicroServiceRegistryApplication.class, args);
    }
}
