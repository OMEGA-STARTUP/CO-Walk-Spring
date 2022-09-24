package com.omega.cowalk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
