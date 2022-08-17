package com.omega.cowalk.security.handler;

import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
        Map<String, String> jwtTokenStore = tokenService.issue(principalUserDetails);

        response.addHeader(JwtTokenProperties.HEADER_ACCESS_KEY, jwtTokenStore.get(JwtTokenProperties.HEADER_ACCESS_KEY));
        response.addHeader(JwtTokenProperties.HEADER_REFRESH_KEY, jwtTokenStore.get(JwtTokenProperties.HEADER_REFRESH_KEY));
        response.getWriter().flush();
    }
}
