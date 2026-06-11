package com.thiago.libraryauth.services;

import com.thiago.libraryauth.infra.config.SecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//    @ExtendWith(SpringExtension.class)
//    @Import({
//            PasswordService.class,
//            SecurityConfig.class,
//    })

//Porque usar desta nova forma
//1. Ela simplifica o ExtendWith + Import
//2. Ela evita o Erro(falso positivo) que a minha variável PasswordService tera devido ao uso do Autowired
    @SpringJUnitConfig(classes = { SecurityService.class, SecurityConfig.class })
    public class SecurityServiceTest {
        @Autowired
        private SecurityService securityService;

        @Test
        public void passwordEncoderTest() {
            String newPassword = "newPassword";

            String encryptedPassword = securityService.encode(newPassword);

            Assertions.assertNotEquals(newPassword, encryptedPassword);
            Assertions.assertTrue(securityService.matches(newPassword, encryptedPassword));
        }
    }
