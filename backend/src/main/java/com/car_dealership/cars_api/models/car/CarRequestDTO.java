package com.car_dealership.cars_api.models.car;

import com.car_dealership.cars_api.models.manufacturer.ManufacturerRequestDTO;

import java.util.Set;

public record CarRequestDTO(String car_name, String model, Integer release_year, String motor, Float kilometers, Float price, ManufacturerRequestDTO manufacturer, Set<String> colors) {
}
