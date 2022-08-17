package com.omega.cowalk;

import com.omega.cowalk.controller.TokenController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CowalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowalkApplication.class, args);
    }

}
