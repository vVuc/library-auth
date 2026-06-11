package com.thiago.libraryauth.exception;

import jakarta.persistence.EntityNotFoundException;

//Por que o exception já não vem como o "Status" assinalado aqui??
public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() { super("User not found"); }
    public UserNotFoundException(String message) { super(message);  }

}
