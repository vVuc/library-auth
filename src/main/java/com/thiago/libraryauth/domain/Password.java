package com.thiago.libraryauth.domain;

//TODO: Consegue explicar claramente oque é um value Object, e quando utilizar eles?
public record Password(String value) {
    public Password {
        validate(value);
    }

    private void validate(String value) {
        //senha precisa ser maior que 8 caracteres
        if (value.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }

        StringBuilder errors = new StringBuilder();

        boolean hasUpperLetter = false;
        boolean hasLowerLetter = false;
        boolean hasNumber = false;
        boolean hasEspecialChar = false;

        //Verifica se a ‘string’ contem 1 letra maiúscula, 1 minúscula, 1 carácter especial e 1 numero
        for (char c : value.toCharArray()) {
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
        if (!hasUpperLetter || !hasLowerLetter || !hasNumber || !hasEspecialChar) {
            errors.append("Password too short");
        }

        if (!hasUpperLetter) {
            errors.append("Password not have a uppercase letter");
        }
        if (!hasLowerLetter) {
            errors.append("Password not have a lower case letter");
        }
        if (!hasNumber) {
            errors.append("Password not have a number");
        }
        if (!hasEspecialChar) {
            errors.append("Password not have a especial character");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid password: " + String.join(", ", errors)
            );
        }
    }
}
