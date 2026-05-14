package com.thiago.libraryauth.infra;

import com.thiago.libraryauth.exception.EmailAlreadyExistException;
import com.thiago.libraryauth.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//Porque ela precisa extender o "ResponseEntityExceptionHandler"
//Oque controllerAdvice Faz
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    //ExceptionHandler é utilizado para que o controllerAdvice consigo entender automaticamente qual .class ele precisa
    //olhar para acionar este método
    @ExceptionHandler(EmailAlreadyExistException.class)
    private ResponseEntity<RestErrorMenssage> userAlreadyExists(EmailAlreadyExistException exception) {
        //RestErrorMenssage SERVE Para padronizar o json de resposta ao cliente
        RestErrorMenssage restErrorMenssage = new RestErrorMenssage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(restErrorMenssage);
    }
    //Os códigos são muito parecidos será que tem uma forma de deixar eles menos repetitivos?
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMenssage> userNotFound(UserNotFoundException exception) {
        RestErrorMenssage restErrorMenssage = new RestErrorMenssage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restErrorMenssage);
    }
}
