package com.example.securityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created Date : 2022/02/17
 *
 * @author zzk
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SecurityServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }
}