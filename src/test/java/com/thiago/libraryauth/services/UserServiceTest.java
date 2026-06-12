package com.thiago.libraryauth.services;

import com.thiago.libraryauth.domain.exceptions.EmailAlreadyExistException;
import com.thiago.libraryauth.domain.exceptions.InvalidCredentialsException;
import com.thiago.libraryauth.domain.exceptions.UserNotFoundException;
import com.thiago.libraryauth.application.service.UserServiceImpl;
import com.thiago.libraryauth.domain.models.User;
import com.thiago.libraryauth.domain.ports.outbound.PasswordHasher;
import com.thiago.libraryauth.domain.ports.outbound.TokenService;
import com.thiago.libraryauth.domain.ports.outbound.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pensnado em um nome")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    @DisplayName("Testes para método de Register")
    class RegisterTests {
        private String nickname;
        private String password;
        private String email;

        @BeforeEach
        public void setUp() {
            nickname = "vVuc";
            password = "Mine2015@";
            email = "email@hotmail.com";
        }

        @Test
        @DisplayName("Cenário: Tentativa de registro com e-mail duplicado")
        public void givenAnExistingEmail_whenRegisteringUser_thenShouldThrowEmailAlreadyExistException() {
            User testUser = User.builder().email(email).nickname(nickname).password(password).build();

            given(userRepository.findByEmail(email)).willReturn(Optional.of(testUser));

            assertThrows(EmailAlreadyExistException.class, () ->userService.register(email, nickname, password));

            then(userRepository).should(never()).save(any());

        }
        @Test
        @DisplayName("Cenário: registro bem sucedido ")
        public void givenAnNewUser_whenRegisteringUser_thenShouldRegisterSuccessfully() {
            given(userRepository.findByEmail(email)).willReturn(Optional.empty());

            userService.register(email, nickname, password);

            then(userRepository).should(times(1)).save(any());

        }
    }

    @Nested
    @DisplayName("Testes para método de login")
    class LoginTests {
        private String email;
        private User user;

        @BeforeEach
        public void setUp() {
            email = "email@hotmail.com";
            String encryptedPassword = "%jasmine2i3!@dk.2e23scad";
            String nickname = "nickname";

            user = User.builder()
                    .email(email)
                    .nickname(nickname)
                    .password(encryptedPassword)
                    .build();
        }

        @Test
        @DisplayName("Cenário: Tentativa de logar usando um email não registrado")
        public void givenAnUserNotRegister_whenTryLogin_shouldThrowUserNotFoundException() {
            String emailNotRegister = "email@yahoo.com";
            String password = "12345678@eE";

            given(userRepository.findByEmail(emailNotRegister)).willReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,()->userService.login(emailNotRegister,password));

            then(tokenService).should(never()).generateToken(any());
        }

        @Test
        @DisplayName("Cenário: Tentativa de logar usando uma senha que não combina com a senha criptografada daquele usuário")
        public void givenPasswordNotMatches_whenTryLogin_shouldThrowInvalidCredentialsException() {
            String passwordNotMatch = "12345678@eE";

            given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
            given(passwordHasher.matches(passwordNotMatch,user.getPassword())).willReturn(false);

            assertThrows(InvalidCredentialsException.class,()->userService.login(email,passwordNotMatch));

            then(tokenService).should(never()).generateToken(any());
        }
        @Test
        @DisplayName("Cenário: Sucesso ao logar usando um email valido e uma senha valida")
        public void givenAnUserExistAndValidCredentials_whenTryLogin_shouldGenerateTokenAndSuccessfullyLogin() {
            String passwordValid = "12345678@eE";

            given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
            given(passwordHasher.matches(passwordValid,user.getPassword())).willReturn(true);

            userService.login(email,passwordValid);

            then(tokenService).should(times(1)).generateToken(user.getEmail());
        }
    }
}