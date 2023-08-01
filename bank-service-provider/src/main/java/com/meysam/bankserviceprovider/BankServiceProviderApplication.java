package com.meysam.bankserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServiceProviderApplication.class, args);
    }

}
