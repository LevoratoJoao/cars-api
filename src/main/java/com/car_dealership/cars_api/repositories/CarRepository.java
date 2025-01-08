package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Cars, Integer> {
}
