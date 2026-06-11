package com.thiago.libraryauth.domain.exceptions;

public class EmailAlreadyExistException extends IllegalStateException {
    public EmailAlreadyExistException() { super("Email already exists"); }
}
