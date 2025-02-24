package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.config.TokenService;
import com.car_dealership.cars_api.config.WebConfig;
import com.car_dealership.cars_api.dto.login.AuthenticationRequestDTO;
import com.car_dealership.cars_api.dto.login.LoginResponseDTO;
import com.car_dealership.cars_api.dto.login.RegisterRequestDTO;
import com.car_dealership.cars_api.models.User;
import com.car_dealership.cars_api.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

//    public AuthenticationController(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }

//    public AuthenticationController(LoginService loginService, TokenService tokenService, AuthenticationManager authenticationManager) {
//        this.tokenService = tokenService;
//        this.loginService = loginService;
//        this.authenticationManager = authenticationManager;
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationRequestDTO authenticationDTO) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> post(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok().body(loginService.register(registerRequestDTO));
    }
}
