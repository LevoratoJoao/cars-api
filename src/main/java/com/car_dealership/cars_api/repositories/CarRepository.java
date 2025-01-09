package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
