package com.car_dealership.cars_api.models.car;

import java.util.Set;

public record CarRequestDTO(String car_name, String brand, String model, Integer release_year, String motor, Float kilometers, Float price, Set<String> colors) {
}
