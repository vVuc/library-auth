package com.thiago.libraryauth.adapters.inbound.controller;

import com.thiago.libraryauth.application.useCase.UserUseCase;
import com.thiago.libraryauth.adapters.inbound.controller.dto.LoginResponseDto;
import com.thiago.libraryauth.adapters.inbound.controller.dto.LoginUserDto;
import com.thiago.libraryauth.adapters.inbound.controller.dto.RegisterUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserUseCase userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginUserDto loginUserDto) {
        String token = userService.login(loginUserDto.email(), loginUserDto.password());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        userService.register(registerUserDto.email(), registerUserDto.nickname(), registerUserDto.password());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader HttpHeaders httpHeaders) {
        userService.logout(httpHeaders.get("Authorization"));
        return ResponseEntity.noContent().build();
    }
}
