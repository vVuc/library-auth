package com.thiago.libraryauth.application.service;

import com.thiago.libraryauth.domain.ports.outbound.PasswordHasher;
import com.thiago.libraryauth.domain.ports.outbound.UserRepository;
import com.thiago.libraryauth.domain.ports.outbound.TokenService;
import com.thiago.libraryauth.application.useCase.UserUseCase;
import com.thiago.libraryauth.domain.models.User;
import com.thiago.libraryauth.domain.exceptions.EmailAlreadyExistException;
import com.thiago.libraryauth.domain.exceptions.InvalidCredentialsException;
import com.thiago.libraryauth.domain.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserUseCase {
    private final PasswordHasher passwordHasher;
    private final UserRepository usersRepository;
    private final TokenService tokenService;

    @Override
    public void register(String email, String nickname, String password) {
        if (usersRepository.findByEmail(email).isPresent()) throw new EmailAlreadyExistException();
        String encryptedPassword = passwordHasher.encode(password);

        var newUser = User.builder()
                .email(email)
                .password(encryptedPassword)
                .nickname(nickname).build();

        usersRepository.save(newUser);
    }

    @Override
    public String login(String email, String password) {

        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordHasher.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        return tokenService.generateToken(user.getEmail());
    }

    @Override
    public void logout(List<String> authHeader) {
        if (authHeader == null || authHeader.isEmpty())
            throw new InvalidCredentialsException("Token not received");
        String bearerToken = authHeader.get(0);
        String token = bearerToken.replace("Bearer ", "");
        var expiresAt = tokenService.getExpiresAtAsInstantFromToken(token);
        Duration tempoRestante = Duration.between(Instant.now(), expiresAt);
        tokenService.invalidateToken(token, tempoRestante.getSeconds());
    }
}
