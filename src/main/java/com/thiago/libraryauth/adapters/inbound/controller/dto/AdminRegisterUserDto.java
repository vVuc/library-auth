package com.thiago.libraryauth.domain.dto;

import com.thiago.libraryauth.domain.models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRegisterUserDto(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser valido")
        String email,
        @NotBlank(message = "A senha é obrigatório")
        @Size(min = 8, message = "Tamanho minimo para senhas é 8 caracteres")
        String password,
        @NotBlank(message = "A nickname é obrigatório")
        String nickname,
        @NotBlank(message = "A Role é obrigatório")
        UserRole role
) {
}
