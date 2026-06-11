package com.thiago.libraryauth.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class User extends BaseDomain{
    @NonNull
    private String email;
    private String password;
    private String nickname;
    @Builder.Default
    private UserRole role = UserRole.USER;
}
