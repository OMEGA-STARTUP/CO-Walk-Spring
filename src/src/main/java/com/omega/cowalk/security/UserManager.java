package com.omega.cowalk.security;

import com.omega.cowalk.domain.CowalkUser;
import com.omega.cowalk.repository.CowalkUserRepository;
import com.omega.cowalk.security.execptions.AuthenticationNotSupportedException;
import com.omega.cowalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//유저를 생성, 로그인, 삭제, 정보 업데이트를 하는 객체
@Component
public class UserManager implements AuthenticationManager
{
    @Autowired
    UserPasswordAuthenticationProvider userPasswordAuthenticationProvider;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<AuthenticationProvider> authenticationProviders = List.of(userPasswordAuthenticationProvider);

        for(AuthenticationProvider authenticationProvider: authenticationProviders){
            if(authenticationProvider.supports(authentication.getClass()))
            {
                Authentication postAuthentication = authenticationProvider.authenticate(authentication);
                if(postAuthentication != null)
                {
                    return postAuthentication;
                }
            }
        }

        throw new AuthenticationNotSupportedException("Authentication type of " + authentication.getClass().toString() + " is not supported!");

    }

    //유저 생성, 이 메써드를 사용할때 비밀번호를 encode할필요 없음
    public CowalkUser createUser(CowalkUser cowalkUser)
    {
        cowalkUser.setPassword(passwordEncoder.encode(cowalkUser.getPassword()));
        return userService.createUser(cowalkUser);
    }
}
