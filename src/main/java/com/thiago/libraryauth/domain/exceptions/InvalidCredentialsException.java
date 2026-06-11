package com.thiago.libraryauth.exception;

//Por que o exception já não vem como o "Status" assinalado aqui??

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() { super("Invalid Credentials"); }
    public InvalidCredentialsException(String message) { super(message);  }

}
