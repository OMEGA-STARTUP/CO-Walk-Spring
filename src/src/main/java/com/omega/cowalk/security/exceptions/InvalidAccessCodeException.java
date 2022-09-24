package com.omega.cowalk.security.exceptions;

public class InvalidAccessCodeException extends RuntimeException
{
    public InvalidAccessCodeException(String msg)
    {
        super(msg);
    }
}
