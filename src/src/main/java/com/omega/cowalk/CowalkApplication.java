package com.omega.cowalk;


import com.omega.cowalk.domain.dto.RegisterRequestDto;
import com.omega.cowalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CowalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowalkApplication.class, args);
    }

//    @Autowired
//    private UserService userService;
//
//    @PostConstruct
//    @Profile("dev")
//    public void initDefaultUser(){
//        RegisterRequestDto registerRequestDto = new RegisterRequestDto(
//                "admin123", "admin123!", "admin123@naver.com", "admin123", "admintest");
//        userService.createUser(registerRequestDto);
//    }
}
