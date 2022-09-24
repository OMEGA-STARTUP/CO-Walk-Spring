package com.omega.cowalk.exception;

public class EmailDuplicateException extends RuntimeException{

    public EmailDuplicateException(String msg)
    {
        super(msg);
    }
}
