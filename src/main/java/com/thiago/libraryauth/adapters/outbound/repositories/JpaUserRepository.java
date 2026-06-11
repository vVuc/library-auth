package com.thiago.libraryauth.adapters.outbound.repositories;

import com.thiago.libraryauth.adapters.outbound.entities.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<JpaUser, UUID> {
    Optional<JpaUser> findByEmail(String email);
}
