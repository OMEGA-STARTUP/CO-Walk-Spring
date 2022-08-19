package com.omega.cowalk.controller.exception_handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.omega.cowalk.domain.ExceptionResult;
import com.omega.cowalk.exception.EmailDuplicateException;
import com.omega.cowalk.exception.IdentifierDuplicateException;
import com.omega.cowalk.exception.NicknameDuplicationException;
import com.omega.cowalk.security.exceptions.InvalidAccessCodeException;
import com.omega.cowalk.security.exceptions.JwtNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
@Slf4j
public class CustomizedResposneEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(InvalidAccessCodeException.class)
    public final ResponseEntity<Object> handleInvalidAccessCodeException(Exception ex, WebRequest request)
    {
        log.debug("handleInvalidAccessCodeException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                ex.getMessage(),
                new Date(),
                "access code is not right!"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtNotFoundException.class)
    public final ResponseEntity<Object> handleJwtNotFoundException(Exception ex, WebRequest request)
    {
        log.debug("handleJwtNotFoundException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                ex.getMessage(),
                new Date(),
                "jwt token is not found!"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public final ResponseEntity<Object> handleJwtVerificationException(Exception ex, WebRequest request)
    {
        log.debug("handleJwtVerificationException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                ex.getMessage(),
                new Date(),
                "token fails to be verified"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NicknameDuplicationException.class, IdentifierDuplicateException.class, EmailDuplicateException.class})
    public final ResponseEntity<Object> handleDuplicationException(Exception ex, WebRequest request)
    {
        log.debug("handleJwtVerificationException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                ex.getMessage(),
                new Date(),
                "it has duplicate value in the field"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.PRECONDITION_FAILED);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers,
             HttpStatus status, WebRequest request)
    {
        log.debug("handleMethodArgumentNotValid is called");

        String rejectedValue = ex.getBindingResult().getFieldError().getRejectedValue() == null? ""
                : ex.getBindingResult().getFieldError().getRejectedValue().toString();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage() == null? ""
                :ex.getBindingResult().getFieldError().getDefaultMessage();

        ExceptionResult exceptionResult = new ExceptionResult(
                "not a valid format",
                new Date(),
                        rejectedValue
                        + " is not valid with message : "
                        + message
        );

        return new ResponseEntity(exceptionResult, HttpStatus.BAD_REQUEST);
    }
}
