package com.thiago.libraryauth.service;

import com.thiago.libraryauth.adapters.outbound.repositories.UserRepositoryImpl;
import com.thiago.libraryauth.infra.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserRepositoryImpl usersRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        var user = usersRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");
        return new SecurityUser(user.get());
    }
}
