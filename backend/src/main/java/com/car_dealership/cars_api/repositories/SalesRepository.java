package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
}
