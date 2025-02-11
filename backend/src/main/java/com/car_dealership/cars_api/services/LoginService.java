package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.login.RegisterRequestDTO;
import com.car_dealership.cars_api.models.User;
import com.car_dealership.cars_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public RegisterRequestDTO register(RegisterRequestDTO registerRequestDTO) {
        if (this.userRepository.findByLogin(registerRequestDTO.login()) != null) {
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.password());
        User newUser = new User(registerRequestDTO.login(), registerRequestDTO.password(), registerRequestDTO.role());

        this.userRepository.save(newUser);

        return registerRequestDTO;
    }
}
