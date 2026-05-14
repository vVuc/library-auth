package com.thiago.libraryauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser valido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        @Size(min = 8, message = "Tamanho minimo para senhas é 8")
        String password,
        @NotBlank(message = "A username é obrigatório")
        String username
) {
}
