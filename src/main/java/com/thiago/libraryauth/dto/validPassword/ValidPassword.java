package com.thiago.libraryauth.dto.validPassword;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


//TODO: PORQUE ESTE CODIGO NÃO FUNCIONA SE EU CRIAR UM CLASSE INTERFACE MAIS SE EU CRIAR UM PUBLIC COM @INTERFACE FUNCIONA?
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//TODO NÃO SEI OQUE É O CONSTRAINT
@Constraint(validatedBy = PasswordValidator.class)
//todo porque @inteface
public @interface ValidPassword {
    String message() default "Senha inválida.";
    //TODO NÃO ENTENDO O SIGNIFICADO
    Class<?>[] groups() default {};
    //TODO NÃO ENTENDO
    Class<? extends Payload>[] payload() default {};
}

