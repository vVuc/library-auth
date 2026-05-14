package com.thiago.libraryauth.repositories;

import com.thiago.libraryauth.domain.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserAuth,Long> {
    boolean existsByEmail(String email);
    Optional<UserAuth> findByEmail(String email);
}
