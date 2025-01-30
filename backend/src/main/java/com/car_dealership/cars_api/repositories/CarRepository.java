package com.car_dealership.cars_api.repositories;

import com.car_dealership.cars_api.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query(value = "SELECT * FROM Car c WHERE c.car_name=:car_name", nativeQuery = true)
    Optional<Car> findByCarName(@Param("car_name") String car_name);

    @Query(value = "select " +
            "c.car_id, " +
            "c.car_name, " +
            "c.model, " +
            "c.release_year, " +
            "c.motor, " +
            "c.kilometers, " +
            "c.price, " +
            "c.manufacturer, " +
            "cl.color_name " +
            "from car c " +
                   "join manufacturer m on c.manufacturer = m.manufacturer_id " +
                   "join car_colors cc on cc.car_id = c.car_id " +
                   "join color cl on cl.color_id = cc.color_id " +
                   "where (:manufacturer_name = '' or m.manufacturer_name like %:manufacturer_name%)" +
                   "and (:model = '' or c.model like %:model%)" +
                   "and (:motor = '' or c.motor like %:motor%)" +
                   "and (:release_year = 0 or c.release_year = %:release_year%)" +
                   "and (c.price >= :min_price and c.price <= :max_price)" +
                   "and (:color = '' or cl.color_name like %:color%)" +
                   "and (:car_name = '' or c.car_name like %:car_name%)"
            , nativeQuery = true)
    Page<Car> findFilteredCars(Pageable pageable,
                               @Param("manufacturer_name") String manufacturer_name,
                               @Param("model") String model,
                               @Param("motor") String motor,
                               @Param("release_year") Integer release_year,
                               @Param("min_price") Float min_price,
                               @Param("max_price") Float max_price,
                               @Param("color") String color,
                               @Param("car_name") String car_name
    );
}
