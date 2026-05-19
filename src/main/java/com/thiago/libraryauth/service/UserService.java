package com.thiago.libraryauth.service;

import com.thiago.libraryauth.domain.Password;
import com.thiago.libraryauth.dto.LoginUserDto;
import com.thiago.libraryauth.dto.RegisterUserDto;
import com.thiago.libraryauth.domain.UserAuth;
import com.thiago.libraryauth.exception.EmailAlreadyExistException;
import com.thiago.libraryauth.exception.InvalidCredentialsException;
import com.thiago.libraryauth.exception.UserNotFoundException;
import com.thiago.libraryauth.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SecurityService securityService;
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;


    public UserAuth register(RegisterUserDto registerUserDto) {
        String email = registerUserDto.email();
        String nickname = registerUserDto.nickname();
        //Mudei a posição da verificação, pois não faz sentido eu fazer algo se o email é invalido
        if (usersRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException();
        }

        //TODO: Passar ISTO PARA UMA NOTATION DENTRO DO MEU DTO
        String passwordValid = new Password(registerUserDto.password()).value();

        String encryptedPassword = securityService.encode(passwordValid);

        var newUser = UserAuth.builder()
                .email(email)
                .password(encryptedPassword)
                .nickname(nickname).build();

        usersRepository.save(newUser);

        return newUser;
    }

    public void login(LoginUserDto loginUserDto) {
        String email = loginUserDto.email();
        String password = loginUserDto.password();
//
//        UserAuth user = usersRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
//
//        if (!securityService.matches(password, user.getPassword())) {
//            throw new InvalidCredentialsException();
//        }

        //Isto gera um token de acesso como se o usuário tive escrito um formulário com os dados de acesso
        //Este "UsernamePasswordAuthenticationToken" é a interface do formulário e no momento que eu dei um new eu
        //criei o formulário com os dados de acesso que o usuário me enviou
        var usernamePassword = new UsernamePasswordAuthenticationToken(email, password);
        //Aqui eu estou tentando validar o formulário basicamente pedindo aos outros componentes que definimos do spring
        //Que valide a autenticação atual.
        var authentication = this.authenticationManager.authenticate(usernamePassword);
    }

}
