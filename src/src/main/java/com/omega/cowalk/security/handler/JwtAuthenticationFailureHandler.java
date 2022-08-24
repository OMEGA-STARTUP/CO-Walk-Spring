package com.omega.cowalk.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.security.exceptions.AuthenticationNotSupportedException;
import com.omega.cowalk.security.exceptions.PasswordNotMatchingException;
import com.omega.cowalk.util.ExceptionResult;
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
import java.util.Date;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(exception instanceof PasswordNotMatchingException){
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception.getMessage(), "비밀번호가 맞지 않습니다.");
        } else if(exception instanceof UsernameNotFoundException){
            setErrorResponse(HttpStatus.NOT_FOUND, response, exception.getMessage(), "존재하지 않는 아이디 입니다.");
        } else if(exception instanceof AuthenticationNotSupportedException){
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception.getMessage(), "요청 파라미터가 부족합니다.");
        } else if(exception instanceof AuthenticationServiceException){
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception.getMessage(), "부적절한 요청입니다.");
        } else {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception.getMessage(), "서버에 문제가 있습니다.");
        }

    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String errorMessage, String details) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(), new ExceptionResult(new Date(), errorMessage, details));
    }
}
