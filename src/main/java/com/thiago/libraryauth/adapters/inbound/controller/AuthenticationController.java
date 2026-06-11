package com.thiago.libraryauth.controller;

import com.thiago.libraryauth.dto.LoginUserDto;
import com.thiago.libraryauth.dto.RegisterUserDto;
import com.thiago.libraryauth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginUserDto loginUserDto) {
        userService.login(loginUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        userService.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
