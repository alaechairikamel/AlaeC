package com.devops.bookshopapi.controller;

import com.devops.bookshopapi.dto.LoginRequest;
import com.devops.bookshopapi.dto.LoginResponse;
import com.devops.bookshopapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
