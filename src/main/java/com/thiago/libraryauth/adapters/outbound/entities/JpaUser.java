package com.thiago.libraryauth.adapters.outbound.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class JpaUser extends JpaBaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Column(length = 30, nullable = false)
    private String role;

}

