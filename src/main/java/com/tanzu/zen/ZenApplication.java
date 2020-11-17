package com.tanzu.zen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class ZenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZenApplication.class, args);
    }

}
