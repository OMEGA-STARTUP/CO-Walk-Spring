package com.omega.cowalk.domain;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResult
{
    private final String msg;
    private final Date timestamp;
    private final String details;



}
