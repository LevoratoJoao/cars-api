package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query(value = "SELECT * FROM Car c WHERE c.car_name=:car_name", nativeQuery = true)
    Optional<Car> findByCarName(@Param("car_name") String car_name);
}
