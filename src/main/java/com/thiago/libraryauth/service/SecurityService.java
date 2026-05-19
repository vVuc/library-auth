package com.thiago.libraryauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO: PORQUE PRECISO DA NOTAÇÃO SERVICE??
@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PasswordEncoder passwordEncoder; // O Lombok cria o construtor para injetar isso

    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}