package com.thiago.libraryauth.application.useCase;

import com.thiago.libraryauth.domain.models.User;
import java.util.List;

public interface UserUseCase {
    User register(String email,String nickname, String password);
    String login(String email, String password);
    void logout(List<String> authHeader);
}
