package com.car_dealership.cars_api.dto.car;

import com.car_dealership.cars_api.dto.manufacturer.ManufacturerRequestDTO;

import java.util.Set;

public record CarRequestDTO(String car_name, String model, Integer release_year, String motor, Float kilometers, Float price, Boolean sold, ManufacturerRequestDTO manufacturer, Set<String> colors) {
}
