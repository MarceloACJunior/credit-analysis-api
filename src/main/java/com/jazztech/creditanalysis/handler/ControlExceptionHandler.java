package com.jazztech.creditanalysis.handler;

import com.jazztech.creditanalysis.handler.exceptions.ClientNotFoundException;
import com.jazztech.creditanalysis.handler.exceptions.InvalidUUIDException;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControlExceptionHandler {
    public static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail clientNotFoundExceptionHandler(ClientNotFoundException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(""));
        problemDetail.setProperty(TIMESTAMP, LocalDateTime.now());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(InvalidUUIDException.class)
    public ProblemDetail invalidUUIDExceptionHandler(InvalidUUIDException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create(""));
        problemDetail.setProperty(TIMESTAMP, LocalDateTime.now());
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
}
