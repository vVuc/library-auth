package com.thiago.libraryauth.infra.security;

import com.thiago.libraryauth.domain.ports.outbound.TokenService;
import com.thiago.libraryauth.domain.ports.outbound.UserRepository;
import com.thiago.libraryauth.domain.models.User;
import com.thiago.libraryauth.domain.exceptions.TokenVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var token = this.recoveryToken(request);
        if (token != null) {
            if (tokenService.isInvalidateToken(token)) throw new TokenVerificationException("Token Revoked");
            String email = tokenService.verifyToken(token);
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) throw new TokenVerificationException("Invalid token");
            SecurityUser securityUser = new SecurityUser(user.get());
            var authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
