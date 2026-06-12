package com.thiago.libraryauth.application.useCase;

import java.util.List;

public interface UserUseCase {
    void register(String email,String nickname, String password);
    String login(String email, String password);
    void logout(List<String> authHeader);
}
