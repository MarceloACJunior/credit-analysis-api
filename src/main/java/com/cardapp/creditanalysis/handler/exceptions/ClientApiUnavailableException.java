package com.cardapp.creditanalysis.handler.exceptions;

public class ClientApiUnavailableException extends RuntimeException {
    public ClientApiUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
