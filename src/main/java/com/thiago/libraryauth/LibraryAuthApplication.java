package com.thiago.libraryauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LibraryAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryAuthApplication.class, args);
    }

}
