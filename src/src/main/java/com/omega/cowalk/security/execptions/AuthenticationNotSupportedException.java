package com.omega.cowalk.security.execptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationNotSupportedException extends AuthenticationException {

    public AuthenticationNotSupportedException(String msg)
    {
        super(msg);
    }
}
