package com.omega.cowalk;

import com.omega.cowalk.domain.dto.CowalkUserDto;
import com.omega.cowalk.domain.entity.CowalkUser;
import com.omega.cowalk.domain.entity.Role;
import com.omega.cowalk.repository.CowalkUserRepository;
import com.omega.cowalk.security.UserPasswordDetailsService;
import com.omega.cowalk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    CowalkUserRepository cowalkUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testUserRepository()
    {
        String encodedPassword = passwordEncoder.encode("Insukkim!6810");
        CowalkUser cowalkUser1 = CowalkUser.builder()
                .identifier("harry!")
                .password(encodedPassword)
                .email("biggie")
                .nickname("geet")
                .role(Role.ROLE_USER)
                .build();

        CowalkUser created = cowalkUserRepository.save(cowalkUser1);

        log.info(created.toString());
    }

    @Autowired
    UserPasswordDetailsService userPasswordDetailsService;

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

            CowalkUserDto cowalkUserDto = new CowalkUserDto(
                    0, "helloee1111", "bassgi111", "sjung12@gmail.com", "hello",
                    Role.ROLE_USER, "htt", null
            );

            Set<ConstraintViolation<CowalkUserDto>> violations = validator.validate(cowalkUserDto);


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

            CowalkUserDto cowalkUserDto = new CowalkUserDto(
                    0, "helloee1111", "bassgi111", "sjung12@gmail.com", "hello",
                    Role.ROLE_USER, null, null
            );

            Set<ConstraintViolation<CowalkUserDto>> violations = validator.validate(cowalkUserDto);


            log.info(String.valueOf(violations.size()));

            if(violations.size()  == 0)
            {
                Optional<CowalkUser> cowalkUser = userService.createUser(cowalkUserDto);
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
