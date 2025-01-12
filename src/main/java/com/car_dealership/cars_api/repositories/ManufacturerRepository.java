package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.manufacturer.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    @Query(value = "SELECT * FROM Manufacturer m WHERE m.man_name = :man_name", nativeQuery = true)
    Optional<Manufacturer> findByManName(@Param("man_name") String man_name);
}
