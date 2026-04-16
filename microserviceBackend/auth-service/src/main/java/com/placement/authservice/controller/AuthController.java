package com.placement.authservice.controller;

import com.placement.authservice.dto.*;
import com.placement.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return service.login(request);
    }

    @PostMapping("/google")
    public AuthResponse googleLogin(@RequestBody GoogleAuthRequest request) {
        return service.googleLogin(request);
    }
}
