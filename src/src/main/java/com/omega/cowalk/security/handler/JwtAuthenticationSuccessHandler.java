package com.omega.cowalk.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.dto.JwtTokenIssueDto;
import com.omega.cowalk.security.token.service.TokenService;
import com.omega.cowalk.util.SuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
        Map<String, String> jwtTokenStore = tokenService.issue(principalUserDetails);

        JwtTokenIssueDto jwtTokenIssueDto =
                new JwtTokenIssueDto(jwtTokenStore.get(JwtTokenProperties.HEADER_ACCESS_KEY), jwtTokenStore.get(JwtTokenProperties.HEADER_REFRESH_KEY));

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(), new SuccessResult(jwtTokenIssueDto));
    }
}
