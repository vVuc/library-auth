package com.thiago.libraryauth.adapters.outbound.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thiago.libraryauth.domain.exceptions.TokenCreationException;
import com.thiago.libraryauth.domain.exceptions.TokenVerificationException;
import com.thiago.libraryauth.domain.ports.outbound.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Component
public class TokenServiceImpl implements TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public String generateToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(email)
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new TokenCreationException("Erro while generating Token");
        }
    }
    @Override
    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
//            throw new RuntimeException("Erro while verification Token", e);
            return "";
        }
    }

    @Override
    public Instant getExpiresAtAsInstantFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getExpiresAtAsInstant();
        } catch (JWTVerificationException e) {
            throw new TokenVerificationException("Erro while getting instant from token");
        }
    }

    @Override
    public void invalidateToken(String token, long tempoRestanteEmSegundos) {
        String chave = "blacklist:" + token;

        redisTemplate.opsForValue().set(chave, "revogado", tempoRestanteEmSegundos, TimeUnit.SECONDS);
    }
    @Override
    public boolean isInvalidateToken(String token) {
        String chave = "blacklist:" + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(chave));
    }
    @Override
    public Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
