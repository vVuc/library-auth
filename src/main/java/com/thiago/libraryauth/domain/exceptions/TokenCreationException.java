package com.thiago.libraryauth.domain.exceptions;

public class TokenCreationException extends RuntimeException {
    public TokenCreationException(String message) {
        super(message);
    }
}
