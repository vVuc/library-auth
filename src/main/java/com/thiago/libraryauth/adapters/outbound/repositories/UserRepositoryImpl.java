package com.thiago.libraryauth.adapters.outbound.repositories;

import com.thiago.libraryauth.domain.User;
import com.thiago.libraryauth.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UsersRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        var jpaUser = jpaUserRepository.findByEmail(email);
        if (jpaUser.isEmpty()) return Optional.empty();
        else return jpaUser.map(userMapper::toDomain);
    }
}
