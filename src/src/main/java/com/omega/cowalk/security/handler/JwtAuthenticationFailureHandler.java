package com.omega.cowalk.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.security.exceptions.AuthenticationNotSupportedException;
import com.omega.cowalk.security.exceptions.PasswordNotMatchingException;
import com.omega.cowalk.util.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Internal server error";
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        if(exception instanceof PasswordNotMatchingException){
            errorMessage = "비밀번호가 맞지 않습니다.";
            status = HttpStatus.BAD_REQUEST.value();
        } else if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 아이디 입니다.";
            status = HttpStatus.NOT_FOUND.value();
        } else if(exception instanceof AuthenticationNotSupportedException){
            errorMessage = "요청 파라미터가 부족합니다.";
            status = HttpStatus.BAD_REQUEST.value();
        } else if(exception instanceof AuthenticationServiceException){
            errorMessage = "부적절한 요청입니다.";
            status = HttpStatus.BAD_REQUEST.value();
        }

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(), new ErrorResult(status, errorMessage));
    }
}
