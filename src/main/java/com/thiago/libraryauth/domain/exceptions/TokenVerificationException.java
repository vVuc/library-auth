package com.thiago.libraryauth.domain.exceptions;

public class TokenVerificationException extends RuntimeException {
    public TokenVerificationException(String message) {
        super(message);
    }
}
