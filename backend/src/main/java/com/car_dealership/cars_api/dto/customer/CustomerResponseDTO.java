package com.car_dealership.cars_api.dto.customer;

public record CustomerResponseDTO(Integer customer_id, String first_name, String last_name, String phone_number, String email) {
}
