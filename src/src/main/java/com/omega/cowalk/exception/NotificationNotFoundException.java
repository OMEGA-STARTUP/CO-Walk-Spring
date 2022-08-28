package com.omega.cowalk.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
