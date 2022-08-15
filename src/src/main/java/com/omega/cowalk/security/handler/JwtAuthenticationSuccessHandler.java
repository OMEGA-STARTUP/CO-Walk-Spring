package com.omega.cowalk.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.creator.JwtTokenCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenCreator jwtTokenCreator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
        String jwtAccessToken = jwtTokenCreator
                .create(principalUserDetails, JwtTokenProperties.ACCESS_TOKEN_EXPIRED_TIME);
        String jwtRefreshToken = jwtTokenCreator
                .create(principalUserDetails, JwtTokenProperties.REFRESH_TOKEN_EXPIRED_TIME);

        response.addHeader(JwtTokenProperties.HEADER_ACCESS_KEY, JwtTokenProperties.HEADER_PREFIX + jwtAccessToken);
        response.addHeader(JwtTokenProperties.HEADER_REFRESH_KEY, JwtTokenProperties.HEADER_PREFIX + jwtRefreshToken);
        response.getWriter().flush();
    }
}
