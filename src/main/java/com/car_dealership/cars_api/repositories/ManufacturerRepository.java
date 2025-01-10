package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.manufacturer.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

}
