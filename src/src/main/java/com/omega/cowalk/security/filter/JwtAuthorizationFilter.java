package com.omega.cowalk.security.filter;

import com.omega.cowalk.repository.UserRepository;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.JwtAuthenticationToken;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.service.TokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final IgnorePathFilterRules ignorePathFilterUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository,
                                  TokenService tokenService, IgnorePathFilterRules ignorePathFilterUtil) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.ignorePathFilterUtil = ignorePathFilterUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
    {
        //ignorePathFilterUtil을 사용해서 필터링을 해야하는지 안해야하는지 정함.
        return ignorePathFilterUtil.shouldNotFilter(this.getClass(), request);
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("인증이나 권한이 필요한 요청: " + request.getRequestURI() );
        PrincipalUserDetails principalUserDetails = tokenService.verifyToken(request.getHeader(JwtTokenProperties.HEADER_ACCESS_KEY), tokenService.getSECRET_KEY());

        if(principalUserDetails != null){
            //Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
            Authentication authentication = new JwtAuthenticationToken(principalUserDetails, null, principalUserDetails.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}

