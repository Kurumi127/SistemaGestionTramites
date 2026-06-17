package com.Sistema.GestionTramites.controller;

import com.Sistema.GestionTramites.dto.auth.LoginRequestDTO;
import com.Sistema.GestionTramites.dto.auth.LoginResponseDTO;
import com.Sistema.GestionTramites.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }
}