package com.car_dealership.cars_api.dto.login;

import com.car_dealership.cars_api.models.UserRole;

public record RegisterRequestDTO(String login, String password, UserRole role) {
}
