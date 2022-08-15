package com.omega.cowalk;

import com.omega.cowalk.domain.dto.RegisterRequestDto;
import com.omega.cowalk.domain.entity.User;
import com.omega.cowalk.domain.entity.Role;
import com.omega.cowalk.repository.UserRepository;
import com.omega.cowalk.security.auth.PrincipalUserDetailsService;
import com.omega.cowalk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@Slf4j
class CowalkApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    UserRepository cowalkUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testUserRepository()
    {
        String encodedPassword = passwordEncoder.encode("Insukkim!6810");
        User cowalkUser1 = User.builder()
                .identifier("harry!")
                .password(encodedPassword)
                .email("biggie")
                .nickname("geet")
                .role(Role.USER)
                .build();

        User created = cowalkUserRepository.save(cowalkUser1);

        log.info(created.toString());
    }

    @Autowired
    PrincipalUserDetailsService userPasswordDetailsService;

    @Test
    void testUserPasswordDetailsService()
    {
        try {
            UserDetails cowalkUserDetails = userPasswordDetailsService.loadUserByUsername("harryjung");
            log.info(cowalkUserDetails.toString());
        }
        catch(Exception e)
        {
            log.error(e.toString());
        }
    }

    @Autowired
    AuthenticationManager authenticationManager;


    @Test
    void testAuthenticationManager(){
        try {
            Authentication preAuth = new UsernamePasswordAuthenticationToken("harryjung0330", "Insukkim!6810");
            Authentication postAuth = authenticationManager.authenticate(preAuth);


            log.info(postAuth.getName());
            log.info(postAuth.getPrincipal().toString());
        }
        catch(Exception e)
        {
            log.error(e.toString());
        }

    }

    @Test
    void testCowalkUserDto(){

        try{
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

            RegisterRequestDto cowalkUserDto = new RegisterRequestDto(
                    "helloee1111", "bassgi111", "sjung12@gmail.com", "hello",
                    "htt", null
            );

            Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(cowalkUserDto);


            log.info(String.valueOf(violations.size()));


            log.info(cowalkUserDto.toString());
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }


    }

    @Autowired
    UserService userService;

    @Test
    void testUserService(){
        try{
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

            RegisterRequestDto cowalkUserDto = new RegisterRequestDto(
                    "helloee1111", "bassgi111", "sjung12@gmail.com", "hello",
                    null, null
            );

            Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(cowalkUserDto);


            log.info(String.valueOf(violations.size()));

            if(violations.size()  == 0)
            {
                Optional<User> cowalkUser = userService.createUser(cowalkUserDto);
                log.info(cowalkUser.get().toString());
            }

            log.info(cowalkUserDto.toString());
        }
        catch (Exception e)
        {
            log.error(e.toString());
        }
    }




}
