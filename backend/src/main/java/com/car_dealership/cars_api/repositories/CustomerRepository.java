package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
