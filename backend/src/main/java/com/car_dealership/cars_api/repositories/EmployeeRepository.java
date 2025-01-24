package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
