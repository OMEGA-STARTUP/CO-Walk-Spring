package com.omega.cowalk.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.domain.dto.LoginRequestDto;
import com.omega.cowalk.security.exceptions.AuthenticationNotSupportedException;
import com.omega.cowalk.security.token.JwtAuthenticationToken;
import com.omega.cowalk.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/user/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        log.debug("사용자 인증 시도");

        // Http Method & Request Header 검증
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isContentTypeJson(request)) {
            throw new AuthenticationServiceException("Authentication method not supported");
        }

        // Http Message Body 검증
        /**
         * JSON 형식으로 넘기지 않았을 때, 예외처리 중
         */
        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);
        if (!StringUtils.hasText(loginRequestDto.getIdentifier()) || !StringUtils.hasText(loginRequestDto.getPassword())) {
            throw new AuthenticationNotSupportedException("Username or Password not provided");
        }

        // 임시 인증 객체 생성
        JwtAuthenticationToken token = new JwtAuthenticationToken(loginRequestDto.getIdentifier(), loginRequestDto.getPassword());

        // AuthenticationManager 에게 인증 위임
        return this.getAuthenticationManager().authenticate(token);
    }
}
