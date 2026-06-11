package com.thiago.libraryauth.exception;

//Por que o exception já não vem como o "Status" assinalado aqui??
public class EmailAlreadyExistException extends IllegalStateException {
    public EmailAlreadyExistException() { super("Email already exists"); }
    public EmailAlreadyExistException(String message) { super(message);  }

}
