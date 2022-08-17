package com.omega.cowalk.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.util.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthorizationExceptionFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            chain.doFilter(request, response);
        } catch(TokenExpiredException e){
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "토큰이 만료되었습니다.");
        } catch(SignatureVerificationException e){
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "부적절한 토큰입니다.");
        } catch(JWTVerificationException e){
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "유효하지 않은 토큰입니다.");
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(), new ErrorResult(status.value(), errorMessage));
    }
}
