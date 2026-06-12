package com.thiago.libraryauth.domain.ports.outbound;

import java.time.Instant;

public interface TokenService {
    String generateToken(String email);

    String verifyToken(String token);

    Instant getExpiresAtAsInstantFromToken(String token);

    void invalidateToken(String token, long tempoRestanteEmSegundos);

    boolean isInvalidateToken(String token);

    Instant getExpirationDate();
}
