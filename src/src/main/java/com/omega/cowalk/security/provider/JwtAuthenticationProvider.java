package com.omega.cowalk.security.provider;

import com.omega.cowalk.security.exceptions.PasswordNotMatchingException;
import com.omega.cowalk.security.token.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService principalUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("실제 사용자 인증 시도");
        String identifier = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = principalUserDetailsService.loadUserByUsername(identifier);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new PasswordNotMatchingException("Invalid password");
        }

        return new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
