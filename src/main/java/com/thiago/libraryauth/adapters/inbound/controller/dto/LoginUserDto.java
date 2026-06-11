package com.thiago.libraryauth.adapters.inbound.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser valido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        String password
) {
}
