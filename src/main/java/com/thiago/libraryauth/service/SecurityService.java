package com.thiago.libraryauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO: PORQUE PRECISO DA NOTAÇÃO SERVICE??
@Service
public class SecurityService {
    private final PasswordEncoder passwordEncoder;

    //TODO: QUAL utilidade do Autowired aqui o java atual não tem um recurso mais moderno apenas com uso do final?
    //Se a sua classe tem apenas um único construtor, o Spring injeta as dependências automaticamente.
    // Ou seja, você pode simplesmente deletar o @Autowired e o código vai continuar funcionando perfeitamente usando apenas o final.

    //Se você quer deixar o código ainda mais moderno e limpo, o padrão de mercado atual no ecossistema Java/Spring é
    //utilizar a biblioteca Lombok com a anotação @RequiredArgsConstructor.

    //Ela gera esse construtor por debaixo dos panos em tempo de compilação para todos os atributos que são final,
    // eliminando totalmente o código boilerplate (repetitivo). O resultado final fica incrivelmente limpo.
    @Autowired
    public SecurityService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

//@Service
//@RequiredArgsConstructor
//public class PasswordService {
//
//    private final PasswordEncoder passwordEncoder; // O Lombok cria o construtor para injetar isso
//
//    public String encode(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
//    }
//
//    public boolean matches(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
//}