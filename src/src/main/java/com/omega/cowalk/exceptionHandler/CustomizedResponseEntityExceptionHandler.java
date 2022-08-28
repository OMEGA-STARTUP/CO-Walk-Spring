package com.omega.cowalk.exceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.omega.cowalk.exception.EmailDuplicateException;
import com.omega.cowalk.exception.IdentifierDuplicateException;
import com.omega.cowalk.exception.NicknameDuplicationException;
import com.omega.cowalk.exception.NotificationNotFoundException;
import com.omega.cowalk.security.exceptions.InvalidAccessCodeException;
import com.omega.cowalk.security.exceptions.JwtNotFoundException;
import com.omega.cowalk.util.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(InvalidAccessCodeException.class)
    public final ResponseEntity<Object> handleInvalidAccessCodeException(Exception ex, WebRequest request)
    {
        log.debug("handleInvalidAccessCodeException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                new Date(),
                ex.getMessage(),
                "access code is not right!"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtNotFoundException.class)
    public final ResponseEntity<Object> handleJwtNotFoundException(Exception ex, WebRequest request)
    {
        log.debug("handleJwtNotFoundException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                new Date(),
                ex.getMessage(),
                "jwt token is not found!"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public final ResponseEntity<Object> handleJwtVerificationException(Exception ex, WebRequest request)
    {
        log.debug("handleJwtVerificationException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                new Date(),
                ex.getMessage(),
                "token fails to be verified"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NicknameDuplicationException.class, IdentifierDuplicateException.class, EmailDuplicateException.class})
    public final ResponseEntity<Object> handleDuplicationException(Exception ex, WebRequest request)
    {
        log.debug("handleJwtVerificationException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                new Date(),
                ex.getMessage(),
                "it has duplicate value in the field"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public final ResponseEntity<Object> handleNotificationNotFoundException(Exception ex, WebRequest request){
        log.debug("handleNotificationNotFoundException is called");

        ExceptionResult exceptionResult = new ExceptionResult(
                new Date(),
                ex.getMessage(),
                "Not found Notification"
        );

        return new ResponseEntity(exceptionResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleInternalServerExceptions(Exception ex, WebRequest request){
        ExceptionResult exceptionResult = new ExceptionResult(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity(exceptionResult, HttpStatus.INTERNAL_SERVER_ERROR);
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
                new Date(),
                "not a valid format",
                        rejectedValue
                        + " is not valid with message : "
                        + message
        );

        return new ResponseEntity(exceptionResult, HttpStatus.BAD_REQUEST);
    }
}
