package com.thiago.libraryauth.adapters.outbound.repositories;

import com.thiago.libraryauth.domain.models.User;
import com.thiago.libraryauth.domain.ports.outbound.UserRepository;
import com.thiago.libraryauth.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        var jpaUser = jpaUserRepository.findByEmail(email);
        return jpaUser.map(userMapper::toDomain);
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(userMapper.toEntity(user));
    }
}
