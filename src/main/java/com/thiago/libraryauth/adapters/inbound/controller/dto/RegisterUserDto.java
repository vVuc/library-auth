package com.thiago.libraryauth.adapters.inbound.controller.dto;

import com.thiago.libraryauth.adapters.inbound.controller.dto.validPassword.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @NotEmpty(message = "The email address is required")
        @Email(message = "The email address must be valid")
        String email,
        @NotEmpty(message = "the password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @ValidPassword
        String password,
        @NotEmpty(message = "The nickname is required")
        String nickname
) {
}
