package com.thiago.libraryauth.domain.ports.outbound;

public interface PasswordHasher {
    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);

}
