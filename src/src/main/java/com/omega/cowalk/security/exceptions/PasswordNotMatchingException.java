package com.omega.cowalk.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordNotMatchingException extends AuthenticationException {
    public PasswordNotMatchingException(String msg)
    {
        super(msg);
    }
}
