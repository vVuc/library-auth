package com.thiago.libraryauth.utils.mapper;

import com.thiago.libraryauth.adapters.outbound.entities.JpaUser;
import com.thiago.libraryauth.domain.models.User;
import com.thiago.libraryauth.domain.models.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(JpaUser jpaUser) {
        if (jpaUser == null) return null;
        return User.builder()
                .id(jpaUser.getId())
                .email(jpaUser.getEmail())
                .password(jpaUser.getPassword())
                .createdAt(jpaUser.getCreatedAt())
                .updatedAt(jpaUser.getUpdatedAt())
                .nickname(jpaUser.getNickname())
                .role(UserRole.valueOf(jpaUser.getRole()))
                .build();
    }

    public JpaUser toEntity(User user) {
        if (user == null) return null;
        JpaUser jpaUser = new JpaUser();
        jpaUser.setId(user.getId());
        jpaUser.setCreatedAt(user.getCreatedAt());
        jpaUser.setUpdatedAt(user.getUpdatedAt());
        jpaUser.setNickname(user.getNickname());
        jpaUser.setEmail(user.getEmail());
        jpaUser.setPassword(user.getPassword());
        jpaUser.setRole(String.valueOf(user.getRole()));

        return jpaUser;
    }
}
