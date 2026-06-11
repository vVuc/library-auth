package com.thiago.libraryauth.adapters.inbound.controller.dto.validPassword;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Senha inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

