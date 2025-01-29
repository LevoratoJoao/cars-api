package com.car_dealership.cars_api.dto.sales;

import java.time.LocalDate;

public record SalesRequestDTO(LocalDate date, Float sale_price, Integer car_id, Integer customer_id, Integer employee_id) {
}
