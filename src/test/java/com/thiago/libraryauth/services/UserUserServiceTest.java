package com.thiago.libraryauth.services;

import com.thiago.libraryauth.dto.LoginUserDto;
import com.thiago.libraryauth.dto.RegisterUserDto;
import com.thiago.libraryauth.domain.UserAuth;
import com.thiago.libraryauth.exception.EmailAlreadyExistException;
import com.thiago.libraryauth.exception.InvalidCredentialsException;
import com.thiago.libraryauth.exception.UserNotFoundException;
import com.thiago.libraryauth.repositories.UsersRepository;
import com.thiago.libraryauth.service.UserService;
import com.thiago.libraryauth.service.SecurityService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

//Estou usando o @ExtendWith(MockitoExtension.class) para mockar os serviços que eu não testarei neste arquivo
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de autenticação")
public class UserUserServiceTest {
    @InjectMocks // O Mockito vai criar o AuthService e injetar o mock abaixo nele automaticamente
    private UserService userService;

    @Mock
    //Criei este mock para conseguir fazer os testes com as exceções sem transformar isto em um teste de integração
    private UsersRepository usersRepository;

    @Mock // Cria um mock puro do Mockito, sem depender do Spring
    private SecurityService securityService;

    @Nested
    @DisplayName("Testes para método de Register")
    class RegisterTests {
        private String username;
        private String password;
        private String encodedPassword;
        private String email;
        private RegisterUserDto dataRegistration;

        //Executa este antes de cada teste
        @BeforeEach
        public void setUp() {
            username = "username";
            password = "Mine2015@";
            encodedPassword = "encodedPassword123";
            email = "email@hotmail.com";

            //Dto levando "input" do usuário até a camada de serviço
            dataRegistration = new RegisterUserDto(email, password, username);
        }

        //Define esta função como uma teste
        @Test
        //Definir o nome é muito importante o padrão é começar com
        //Should = deve; e complementar como oque que ele deve fazer
        // Register = registrar; User = Usuário; SuccessFully = Com sucesso
        public void shouldRegisterUserSuccessfully() {
            // Configurando o comportamento do Mock
            Mockito.when(securityService.encode(password)).thenReturn(encodedPassword);

            UserAuth newUser = userService.register(dataRegistration);

            Assertions.assertEquals(encodedPassword, newUser.getPassword());
            Assertions.assertEquals(email, newUser.getEmail());
            Assertions.assertEquals(username, newUser.getUsername());

            Mockito.verify(securityService).encode(password);
            Mockito.verify(usersRepository).save(newUser);


        }

        @Test
        //Deve lançar uma exceção quando o usuário já existir no sistema
        public void shouldThrowExceptionWhenUserAlreadyExists() {
            Mockito.when(usersRepository.existsByEmail(email)).thenReturn(true);
            EmailAlreadyExistException exception = Assertions.assertThrows(
                    EmailAlreadyExistException.class,
                    () -> {
                        userService.register(dataRegistration);
                    }
            );

            Assertions.assertEquals("Email already exists", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes para método de login")
    class LoginTests {
        private UserAuth user;
        private LoginUserDto loginUserDto;

        //Executa este antes de cada teste
        @BeforeEach
        public void setUp() {
            String password = "Mine2015@";
            String email = "email@hotmail.com";
            String username = "username";

            user = new UserAuth();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);

            loginUserDto = new LoginUserDto(email, password);
        }

        @Test
        public void shouldLoginSuccessfully() {
            String password = loginUserDto.password();
            String email = loginUserDto.email();

            Mockito.when(usersRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
            Mockito.when(securityService.matches(password, user.getPassword())).thenReturn(true);

            userService.login(loginUserDto);
        }

        @Test
        public void shouldThrowExceptionWhenUserNotFound() {
            String email = loginUserDto.email();

            Mockito.when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

            UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class, () -> userService.login(loginUserDto));

            Assertions.assertEquals("User not found", exception.getMessage());
        }
        @Test
        public void shouldThrowExceptionWhenPasswordNotMatch() {
            String email = loginUserDto.email();
            String password = loginUserDto.password();

            Mockito.when(usersRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
            Mockito.when(securityService.matches(password, user.getPassword())).thenReturn(false);

            InvalidCredentialsException exception = Assertions.assertThrows(InvalidCredentialsException.class, () -> userService.login(loginUserDto));

            Assertions.assertEquals("Invalid Credentials", exception.getMessage());
        }
    }
}