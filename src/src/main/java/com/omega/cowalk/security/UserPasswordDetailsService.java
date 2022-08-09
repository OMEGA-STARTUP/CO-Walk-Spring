package com.omega.cowalk.security;

import com.omega.cowalk.domain.CowalkUser;
import com.omega.cowalk.repository.CowalkUserRepository;
import com.omega.cowalk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UserPasswordDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CowalkUser user = null;

        try{
            user = userService.findByIdentifier(username);

        }catch(Exception e)
        {
            log.error("error occurred while getting user: " + e.toString());
        }

        if(user == null)
        {
            throw new UsernameNotFoundException("username "+ username + " not found!");
        }

        return new CowalkUserDetails(user);

    }
}
