package com.thiago.libraryauth.infra.security;

import com.thiago.libraryauth.domain.ports.outbound.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");
        return new SecurityUser(user.get());
    }
}
