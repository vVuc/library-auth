package com.thiago.libraryauth.adapters.inbound.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(

        @NotBlank(message = "The email address is required")
        @Email(message = "The email address must be valid")
        String email,
        @NotBlank(message = "The password is required")
        String password
) {
}
