package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT * FROM Customer c " +
                    "WHERE (c.first_name like %:first_name% OR :first_name = '') " +
                    "AND (c.last_name like %:last_name% OR :last_name = '') " +
                    "AND (c.email like %:email% OR :email = '') " +
                    "AND (c.phone_number like %:phone_number% OR :phone_number = '') "
            , nativeQuery = true)
    Page<Customer> findFilteredCustomer(Pageable pageable,
                                        @Param("first_name") String first_name,
                                        @Param("last_name") String last_name,
                                        @Param("email") String email,
                                        @Param("phone_number") String phone_number
    );
}
