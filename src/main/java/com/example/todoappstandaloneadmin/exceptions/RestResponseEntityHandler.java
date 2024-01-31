package com.example.todoappstandaloneadmin.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityHandler extends ResponseEntityExceptionHandler {
    /**
     * A method to handle IllegalArgumentException and IllegalStateException.
     *
     * @param  ex       the RuntimeException
     * @param  request  the WebRequest
     * @return         a ResponseEntity<Object> as the response
     */
    @ExceptionHandler (value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Bad Request";
        return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}