package com.thiago.libraryauth.adapters.outbound.security;

import com.thiago.libraryauth.domain.ports.outbound.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHasherImpl implements PasswordHasher {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword,encodedPassword);
    }
}
