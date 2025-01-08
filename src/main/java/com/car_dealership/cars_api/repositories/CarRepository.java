package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
