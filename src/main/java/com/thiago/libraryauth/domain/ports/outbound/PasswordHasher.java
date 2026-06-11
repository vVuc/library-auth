package com.thiago.libraryauth.domain.ports.inbound;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordHasher {
    public String encode(String rawPassword);

    public boolean matches(String rawPassword, String encodedPassword);

}
