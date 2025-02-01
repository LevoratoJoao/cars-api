package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
    @Query(value = BASE_SELECT +
            FROM_JOIN_CLAUSE +
            WHERE_CLAUSE,
            nativeQuery = true)
    Page<Sales> findFilteredSales(Pageable pageable,
                                  @Param("date") LocalDate date,
                                  @Param("min_price") Float min_price,
                                  @Param("max_price") Float max_price,
                                  @Param("car_name") String car_name,
                                  @Param("customer_name") String customer_name,
                                  @Param("employee_name") String employee_name
    );

    static final String BASE_SELECT = "SELECT " +
            "s.sales_id, " +
            "s.sale_date, " +
            "s.sale_price, " +
            "c.car_id, " +
            "ctm.customer_id, " +
            "e.employee_id ";

    static final String FROM_JOIN_CLAUSE =
            "FROM Sales s " +
                    "JOIN Car c ON s.car_id = c.car_id " +
                    "JOIN Customer ctm ON s.customer_id = ctm.customer_id " +
                    "JOIN Employee e ON s.employee_id = e.employee_id ";

    static final String WHERE_CLAUSE =
            "WHERE (s.sale_price >= :min_price AND s.sale_price <= :max_price) " +
                    "AND (c.car_name LIKE %:car_name% OR :car_name = '') " +
                    "AND (ctm.first_name || ' ' || ctm.last_name LIKE %:customer_name% OR :customer_name = '') " +
                    "AND (e.first_name || ' ' || e.last_name LIKE %:employee_name% OR :employee_name = '')";

}
