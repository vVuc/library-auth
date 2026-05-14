package com.thiago.libraryauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//TODO: Eu não intendo porque definir o nome do Bean que criamos como PasswordEncoder sendo que já existe essa classe no spring
import org.springframework.security.crypto.password.PasswordEncoder;


//TODO: Oque Configuration faz?
@Configuration
public class SecurityConfig {
    //Bean é uma annotations para identificar partes que precisam ser gerenciadas pelo spring boot durante a implementação
    //Assim como também fazer uma injeção automática das dependências necessárias
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
