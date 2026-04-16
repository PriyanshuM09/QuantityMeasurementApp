package com.placement.authservice.service;

import com.placement.authservice.dto.*;
import com.placement.authservice.entity.User;
import com.placement.authservice.repository.UserRepository;
import com.placement.authservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final GoogleTokenVerifier googleTokenVerifier;

    public String register(AuthRequest request) {
        repository.findByUsername(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("User already exists");
                });

        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        repository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(AuthRequest request) {

        User user = repository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPassword() == null) {
            throw new RuntimeException("Password login is not available for this account");
        }

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }

    public AuthResponse googleLogin(GoogleAuthRequest request) {
        GoogleUserInfo googleUser = googleTokenVerifier.verify(request.getIdToken());

        User user = repository.findByGoogleId(googleUser.subject())
                .orElseGet(() -> repository.findByUsername(googleUser.email())
                        .map(existingUser -> {
                            existingUser.setGoogleId(googleUser.subject());
                            return existingUser;
                        })
                        .orElseGet(() -> {
                            User newUser = new User();
                            newUser.setUsername(googleUser.email());
                            newUser.setGoogleId(googleUser.subject());
                            return newUser;
                        }));

        repository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
