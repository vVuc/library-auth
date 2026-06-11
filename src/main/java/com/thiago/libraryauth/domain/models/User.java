package com.thiago.libraryauth.domain.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
