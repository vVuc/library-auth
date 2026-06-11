package com.thiago.libraryauth.domain.dto;

import com.thiago.libraryauth.domain.dto.validPassword.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @NotEmpty(message = "O email é obrigatório")
        @Email(message = "O email deve ser valido")
        String email,
        @NotEmpty(message = "A senha é obrigatório")
        @Size(min = 8, message = "Tamanho minimo para senhas é 8 caracteres")
        @ValidPassword
        String password,
        @NotEmpty(message = "A nickname é obrigatório")
        String nickname
) {
}
