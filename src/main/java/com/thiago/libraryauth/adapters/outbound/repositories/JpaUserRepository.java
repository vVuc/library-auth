package com.thiago.libraryauth.adapters.outbound.repositories;

import com.thiago.libraryauth.adapters.outbound.entities.JpaUser;
import com.thiago.libraryauth.domain.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<JpaUser,UUID> {
    Optional<JpaUser> findByEmail(String email);
}
