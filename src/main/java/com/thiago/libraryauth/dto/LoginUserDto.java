package com.thiago.libraryauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserDto(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser valido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        String password
) {
}
