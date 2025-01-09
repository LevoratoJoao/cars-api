package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "SELECT * FROM Color c WHERE c.color_name = :color_name", nativeQuery = true)
    public Optional<Color> findColorByColorName(@Param("color_name") String color_name);
}
