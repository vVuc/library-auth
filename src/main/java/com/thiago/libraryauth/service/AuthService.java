package com.thiago.libraryauth.service;

import com.thiago.libraryauth.domain.Password;
import com.thiago.libraryauth.dto.LoginUserDto;
import com.thiago.libraryauth.dto.RegisterUserDto;
import com.thiago.libraryauth.domain.UserAuth;
import com.thiago.libraryauth.exception.EmailAlreadyExistException;
import com.thiago.libraryauth.exception.InvalidCredentialsException;
import com.thiago.libraryauth.exception.UserNotFoundException;
import com.thiago.libraryauth.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private SecurityService securityService;
    private UsersRepository usersRepository;

    public UserAuth register(RegisterUserDto registerUserDto) {
        String email = registerUserDto.email();
        String username = registerUserDto.username();
        //Mudei a posição da verificação, pois não faz sentido eu fazer algo se o email é invalido
        if (usersRepository.existsByEmail(email)){
            throw new EmailAlreadyExistException();
        }

        //TODO: Passar ISTO PARA UMA NOTATION DENTRO DO MEU DTO
        String passwordValid = new Password(registerUserDto.password()).value();

        //TODO: O PASSWORD PRECISA MESMO SER UM SERVIÇO ELE N PODE SER APENAS UM UTILS
        String encryptedPassword = securityService.encode(passwordValid);

        //TODO: ISTO É FEIO
        var newUser = new UserAuth();
        newUser.setEmail(registerUserDto.email());
        newUser.setPassword(encryptedPassword);
        newUser.setUsername(username);

        usersRepository.save(newUser);

        return newUser;
    }

    public void login(LoginUserDto loginUserDto) {
        String email = loginUserDto.email();
        String password = loginUserDto.password();

        UserAuth user = usersRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (!securityService.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }
    }

}
