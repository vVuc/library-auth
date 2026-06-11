package com.thiago.libraryauth.domain.ports.outbound;

import com.thiago.libraryauth.domain.models.User;

import java.time.Instant;

public interface TokenService {
    String generateToken(User user);

    String verifyToken(String token);

    Instant getExpiresAtAsInstantFromToken(String token);

    void invalidateToken(String token, long tempoRestanteEmSegundos);

    boolean isInvalidateToken(String token);

    Instant getExpirationDate();
}
