package com.car_dealership.cars_api.dto.employee;

import java.time.LocalDate;

public record EmployeeRequestDTO(String first_name, String last_name, String position, Float salary, LocalDate hire_date, String phone_number, String email) {
}
