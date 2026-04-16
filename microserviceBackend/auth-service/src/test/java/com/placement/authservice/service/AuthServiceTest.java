package com.placement.authservice.service;

import com.placement.authservice.dto.AuthRequest;
import com.placement.authservice.dto.AuthResponse;
import com.placement.authservice.dto.GoogleAuthRequest;
import com.placement.authservice.entity.User;
import com.placement.authservice.repository.UserRepository;
import com.placement.authservice.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private GoogleTokenVerifier googleTokenVerifier;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerThrowsWhenUserAlreadyExists() {
        AuthRequest request = new AuthRequest();
        request.setEmail("user@example.com");

        when(repository.findByUsername("user@example.com")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(request));

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void googleLoginCreatesUserAndReturnsJwt() {
        GoogleAuthRequest request = new GoogleAuthRequest();
        request.setIdToken("google-token");

        when(googleTokenVerifier.verify("google-token"))
                .thenReturn(new GoogleUserInfo("google-sub", "user@example.com", "User"));
        when(repository.findByGoogleId("google-sub")).thenReturn(Optional.empty());
        when(repository.findByUsername("user@example.com")).thenReturn(Optional.empty());
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtUtil.generateToken("user@example.com")).thenReturn("jwt-token");

        AuthResponse response = authService.googleLogin(request);

        assertEquals("jwt-token", response.getToken());
        verify(repository).save(any(User.class));
    }
}
