package com.omega.cowalk.security.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtNotFoundException extends JWTVerificationException {
    public JwtNotFoundException(String message) {
        super(message);
    }
}
