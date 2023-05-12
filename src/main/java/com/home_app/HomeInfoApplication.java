package com.home_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomeInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeInfoApplication.class, args);
    }

}
