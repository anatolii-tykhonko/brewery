package com.edvantis.learning.brewery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BreweryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreweryApplication.class, args);
    }

}
