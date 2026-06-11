package com.thiago.libraryauth.application.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.thiago.libraryauth.adapters.outbound.repositories.UserRepositoryImpl;
import com.thiago.libraryauth.application.useCase.UserUseCase;
import com.thiago.libraryauth.domain.User;
import com.thiago.libraryauth.dto.LoginUserDto;
import com.thiago.libraryauth.dto.RegisterUserDto;
import com.thiago.libraryauth.exception.EmailAlreadyExistException;
import com.thiago.libraryauth.exception.InvalidCredentialsException;
import com.thiago.libraryauth.exception.UserNotFoundException;
import com.thiago.libraryauth.infra.redis.TokenBlacklistService;
import com.thiago.libraryauth.infra.security.SecurityService;
import com.thiago.libraryauth.infra.security.SecurityUser;
import com.thiago.libraryauth.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final SecurityService securityService;
    private final UserRepositoryImpl usersRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final TokenBlacklistService blacklistService;

    @Override
    public User register(RegisterUserDto registerUserDto) {
        String email = registerUserDto.email();
        String nickname = registerUserDto.nickname();
        if (usersRepository.findByEmail(registerUserDto.email()).isPresent()) throw new EmailAlreadyExistException();
        String encryptedPassword = securityService.encode(registerUserDto.password());

        var newUser = User.builder()
                .email(email)
                .password(encryptedPassword)
                .nickname(nickname).build();

        usersRepository.save(newUser);

        return newUser;
    }

    @Override
    public String login(LoginUserDto loginUserDto) {
        String email = loginUserDto.email();
        String password = loginUserDto.password();

        var usernamePassword = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        if (user == null) throw new UserNotFoundException("Não foi possível recuperar o usuarios");
        return tokenService.generateToken(user.getUser());
    }

    @Override
    public void logout(HttpHeaders httpHeaders) {
        //PEgar jwt que esta no meu header
        List<String> authHeader = httpHeaders.get("Authorization");
        if (authHeader == null || authHeader.isEmpty())
            throw new InvalidCredentialsException("Token não foi enviado para logout");
        String bearerToken = authHeader.get(0);
        String token = bearerToken.replace("Bearer ", "");
        //Enviar o jwt para o o banco redis (LISTA NEGRA)
        var expiresAt = tokenService.getExpiresAtAsInstantFromToken(token);
        Duration tempoRestante = Duration.between(Instant.now(), expiresAt);
        //Invalidar autenticação do cliente atual
        blacklistService.invalidarToken(token, tempoRestante.getSeconds());
    }
}
