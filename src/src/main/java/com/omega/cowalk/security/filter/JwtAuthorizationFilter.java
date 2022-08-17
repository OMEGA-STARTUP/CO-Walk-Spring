package com.omega.cowalk.security.filter;

import com.omega.cowalk.domain.entity.User;
import com.omega.cowalk.repository.UserRepository;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.JwtAuthenticationToken;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.service.TokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("인증이나 권한이 필요한 요청");
        String identifier = tokenService.verifyToken(request.getHeader(JwtTokenProperties.HEADER_ACCESS_KEY), tokenService.getSECRET_KEY());

        User user = userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username"));

        PrincipalUserDetails principalDetails = new PrincipalUserDetails(user);
        //Authentication 객체를 만들어 준다.
        Authentication authentication = new JwtAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
