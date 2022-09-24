package com.omega.cowalk.security.auth;

import com.omega.cowalk.domain.entity.user.User;
import com.omega.cowalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // catch 문으로 잡아서 처리해주면 handler에서 Null 값이 들어가기 때문에 없앰
        log.debug("Check user identifier");
        User user = userRepository.findByIdentifier(username).orElseThrow(
                () -> new UsernameNotFoundException("username "+ username + " not found!"));
        return new PrincipalUserDetails(user);
    }
}
