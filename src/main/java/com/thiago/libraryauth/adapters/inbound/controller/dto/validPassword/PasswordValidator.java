package com.thiago.libraryauth.adapters.inbound.controller.dto.validPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

// 2. O VALIDADOR (Onde a mágica acontece)
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String passwordValue, ConstraintValidatorContext context) {
        if (passwordValue == null) return true;

        List<String> errors = new ArrayList<>();

        boolean hasUpperLetter = false;
        boolean hasLowerLetter = false;
        boolean hasNumber = false;
        boolean hasEspecialChar = false;

        //Verifica se a ‘string’ contem 1 letra maiúscula, 1 minúscula, 1 carácter especial e 1 numero
        for (char c : passwordValue.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperLetter = true;
            }

            if (Character.isLowerCase(c)) {
                hasLowerLetter = true;
            }

            if (Character.isDigit(c)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                hasEspecialChar = true;
            }

        }

        if (!hasUpperLetter) errors.add("Password not have a uppercase letter");
        if (!hasLowerLetter) errors.add("Password not have a lower case letter");
        if (!hasNumber) errors.add("Password not have a number");
        if (!hasEspecialChar) errors.add("Password not have a especial character");

        if (errors.isEmpty()) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        String customMessage = "Senha inválida: " + String.join(", ", errors) + ".";

        context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
        return false;
    }
}
