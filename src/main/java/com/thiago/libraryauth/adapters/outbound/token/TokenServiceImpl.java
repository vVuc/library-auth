package com.thiago.libraryauth.adapters.outbound.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thiago.libraryauth.domain.User;
import com.thiago.libraryauth.exception.TokenCreationException;
import com.thiago.libraryauth.exception.TokenVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new TokenCreationException("Erro while generating Token");
        }
    }

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


    // Método para incluir o token na lista negra durante o logout
    public void invalidarToken(String token, long tempoRestanteEmSegundos) {
        // Salva a chave "blacklist:TOKEN" com o valor "revogado"
        String chave = "blacklist:" + token;

        // O Redis vai deletar esse token automaticamente assim que o tempo expirar!
        redisTemplate.opsForValue().set(chave, "revogado", tempoRestanteEmSegundos, TimeUnit.SECONDS);
    }

    // Método que o seu API Gateway (ou serviço) usará para checar se está banido
    public boolean isTokenInvalido(String token) {
        String chave = "blacklist:" + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(chave));
    }

    public Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
