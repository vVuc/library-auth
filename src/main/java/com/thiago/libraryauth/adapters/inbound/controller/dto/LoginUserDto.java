package com.thiago.libraryauth.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginUserDto(

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser valido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        String password
) {
}
