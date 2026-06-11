package com.thiago.libraryauth.adapters.outbound.token;

import com.thiago.libraryauth.domain.models.User;

import java.time.Instant;

public interface TokenService {
    public String generateToken(User user);

    public String verifyToken(String token);

    public Instant getExpiresAtAsInstantFromToken(String token);

    public void invalidateToken(String token, long tempoRestanteEmSegundos);

    public boolean isInvalidateToken(String token);

    public Instant getExpirationDate();
}
