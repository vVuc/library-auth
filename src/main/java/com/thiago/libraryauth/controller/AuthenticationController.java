package com.thiago.libraryauth.controller;

import com.thiago.libraryauth.dto.LoginUserDto;
import com.thiago.libraryauth.dto.RegisterUserDto;
import com.thiago.libraryauth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Diz ao spring que essa classe ira funcionar no sistema como um controller recebendo e gerenciado requisições
//Especificamente como Um RestController, pois também podemos ter alguns controller como Web que devolve um pagina web
@RestController
//RequestMapping mapeia as requisições que deve acionar os metodos desta classe
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginUserDto loginUserDto) {
        userService.login(loginUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        userService.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
