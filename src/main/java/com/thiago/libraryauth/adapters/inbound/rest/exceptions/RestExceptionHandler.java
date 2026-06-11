package com.thiago.libraryauth.infra;

import com.thiago.libraryauth.domain.exceptions.EmailAlreadyExistException;
import com.thiago.libraryauth.domain.exceptions.TokenCreationException;
import com.thiago.libraryauth.domain.exceptions.TokenVerificationException;
import com.thiago.libraryauth.domain.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyExists(EmailAlreadyExistException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restErrorMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFound(UserNotFoundException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMessage);
    }

    @ExceptionHandler(TokenCreationException.class)
    private ResponseEntity<RestErrorMessage> tokenCreation(TokenCreationException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restErrorMessage);
    }

    @ExceptionHandler(TokenVerificationException.class)
    private ResponseEntity<RestErrorMessage> tokenVerification(TokenVerificationException exception) {
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            org.springframework.http.HttpStatusCode status,
            org.springframework.web.context.request.WebRequest request
    ) {
        // 1. Coleta todos os erros de campos do DTO e junta em uma única String
        List<RestFieldError> errors = ex.getFieldErrors().stream()
                .map(fieldError -> new RestFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        RestErrorRequest errorMessage = new RestErrorRequest(HttpStatus.BAD_REQUEST, errors);

        // 3. Retorna a resposta padronizada com o status 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
