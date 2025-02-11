package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.login.AuthenticationRequestDTO;
import com.car_dealership.cars_api.dto.login.LoginResponseDTO;
import com.car_dealership.cars_api.dto.login.RegisterRequestDTO;
import com.car_dealership.cars_api.models.User;
import com.car_dealership.cars_api.services.LoginService;
import com.car_dealership.cars_api.services.TokenService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private LoginService loginService;

    public AuthenticationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO authenticationDTO) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterRequestDTO> post(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok().body(loginService.register(registerRequestDTO));
    }
}
