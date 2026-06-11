package com.thiago.libraryauth.adapters.outbound.repositories;

import com.thiago.libraryauth.domain.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);

}
