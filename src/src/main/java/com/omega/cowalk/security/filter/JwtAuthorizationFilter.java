package com.omega.cowalk.security.filter;

import com.omega.cowalk.repository.UserRepository;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.service.TokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
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
        tokenService.verifyToken(request.getHeader(JwtTokenProperties.HEADER_ACCESS_KEY), tokenService.getSECRET_KEY());

        chain.doFilter(request, response);
    }
}
