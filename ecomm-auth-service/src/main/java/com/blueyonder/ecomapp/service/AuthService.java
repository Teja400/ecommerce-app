package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.model.LoginResponse;
import com.blueyonder.ecomapp.model.UserPrincipal;
import com.blueyonder.ecomapp.security.JwtIssuer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        log.info("User Authentication Success. Username: {}, Email: {}, Roles: {}", principal.getUsername(), principal.getEmail(), roles);

        var token = jwtIssuer.issue(JwtIssuer.Request.builder()
                .username(principal.getUsername())
                .email(principal.getEmail())
                .roles(roles)
                .build());

        log.info("Issuing JWT Token {}", token);

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
