package com.omega.cowalk.security;

import com.omega.cowalk.security.execptions.PasswordNotMatchingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserPasswordAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    UserPasswordDetailsService userPasswordDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userPasswordDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, userDetails.getPassword()))
        {
            UsernamePasswordAuthenticationToken temp =  new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

            //token에 CowalkUserDetails를 저장하여, 나중에 토큰을 만들때 user_id를 가져올수 있도록
            temp.setDetails(userDetails);

            return temp;
        }
        else{
            throw new PasswordNotMatchingException("wrong password!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
