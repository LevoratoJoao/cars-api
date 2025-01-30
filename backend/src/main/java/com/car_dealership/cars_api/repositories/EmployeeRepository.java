package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM Employee e " +
                    "WHERE (e.first_name like %:first_name% OR :first_name = '') " +
                    "AND (e.last_name like %:last_name% OR :last_name = '') " +
                    "AND (e.position like %:position% OR :position = '') " +
                    "AND (e.email like %:email% OR :email = '') " +
                    "AND (e.salary >= :min_salary and e.salary <= :max_price) " +
                    "AND (:hire_date IS NULL OR e.hire_date = :hire_date) " +
                    "AND (:phone_number = '' OR e.phone_number like %:phone_number%) "
                    , nativeQuery = true)
    Page<Employee> findFilteredEmployees(Pageable pageable,
                                        @Param("first_name") String first_name,
                                        @Param("last_name") String last_name,
                                        @Param("position") String position,
                                        @Param("email") String email,
                                        @Param("min_salary") Float min_salary,
                                        @Param("max_price") Float max_price,
                                        @Param("hire_date") LocalDate hire_date,
                                        @Param("phone_number") String phone_number
    );
}
