package com.omega.cowalk;

import com.omega.cowalk.domain.CowalkUser;
import com.omega.cowalk.repository.CowalkUserRepository;
import com.omega.cowalk.security.CowalkUserDetails;
import com.omega.cowalk.security.UserManager;
import com.omega.cowalk.security.UserPasswordAuthenticationProvider;
import com.omega.cowalk.security.UserPasswordDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

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
        CowalkUser cowalkUser = new CowalkUser( "harryjung0330", encodedPassword, "harryjung0330@gmail.com",
        "안녕", "ROLE_USER");

        CowalkUser created = cowalkUserRepository.save(cowalkUser);

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
    UserPasswordAuthenticationProvider userPasswordAuthenticationProvider;

    @Test
    void testUserPasswordAuthenticationProvider(){
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken("harryjung0330", "Insukkim!6810");

            Authentication postAuthentication = userPasswordAuthenticationProvider.authenticate(authentication);

            log.info(postAuthentication.getName());
            log.info(postAuthentication.getAuthorities().toString());

            CowalkUserDetails cowalkUserDetails = null;

            if(postAuthentication.getDetails() instanceof CowalkUserDetails)
            {
                cowalkUserDetails = (CowalkUserDetails) postAuthentication.getDetails();
                log.info(cowalkUserDetails.toString());
            }
            else{
                log.info("problem with details of Authentication");
                log.info(postAuthentication.getDetails().toString());
            }
        }
        catch(Exception e)
        {
            log.error(e.toString());
        }
    }

    @Autowired
    UserManager userManager;

    @Test
    void testUserManagerAuthentication()
    {
        try {
            Authentication preAuth = new UsernamePasswordAuthenticationToken("harryjung030", "Inukkim!6810");
            Authentication postAuth = userManager.authenticate(preAuth);

            log.info(postAuth.toString());
            log.info(postAuth.getDetails().toString());
        }
        catch(Exception e)
        {
            log.error(e.toString());
        }

    }

    @Test
    void testUserManagerCreateUser()
    {
        try{
            CowalkUser cowalkUser = new CowalkUser("biggie", "6810", "sjung112@gmail.com",
                    "라면만", CowalkUser.ROLE_USER );
            userManager.createUser(cowalkUser);
        }
        catch(Exception e)
        {
            log.error(e.toString());
        }
    }
}
