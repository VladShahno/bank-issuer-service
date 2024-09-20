package com.demo.project.bankissuerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankIssuerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankIssuerServiceApplication.class, args);
    }
}
