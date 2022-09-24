package com.omega.cowalk.security.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtMalformedException extends JWTVerificationException {
    public JwtMalformedException(String msg) {
        super(msg);
    }
}
