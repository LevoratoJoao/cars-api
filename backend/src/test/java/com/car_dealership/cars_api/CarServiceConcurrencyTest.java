package com.car_dealership.cars_api;

import com.car_dealership.cars_api.dto.car.CarRequestDTO;
import com.car_dealership.cars_api.dto.car.CarResponseDTO;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.models.Car;
import com.car_dealership.cars_api.models.Color;
import com.car_dealership.cars_api.models.Manufacturer;
import com.car_dealership.cars_api.repositories.CarRepository;
import com.car_dealership.cars_api.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarServiceConcurrencyTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    @Transactional
    public void testConcurrentCarUpdates() throws InterruptedException {
        // Initialize car and save to the database


        // Create two threads simulating two customers updating the same car
        Thread customer1 = new Thread(() -> {
            try {
                CarRequestDTO updatedCar = new CarRequestDTO( "Toyota Corolla", "Sedan", 2020, "V4", 20000.0f, 19000.00f, new ManufacturerRequestDTO("Toyota", "Japan"), Set.of("Red", "White"));
                carService.updateCar(2, updatedCar);
            } catch (Exception e) {
                System.out.println("Customer 1: " + e.getMessage());
            }
        });

        Thread customer2 = new Thread(() -> {
            try {
                CarRequestDTO updatedCar = new CarRequestDTO("Toyota Corolla", "Sedan", 2020, "V4", 15000.5f, 18000.00f, new ManufacturerRequestDTO("Toyota", "Japan"), Set.of("Red", "White"));
                carService.updateCar(2, updatedCar);
            } catch (Exception e) {
                System.out.println("Customer 2: " + e.getMessage());
            }
        });

        // Start both threads
        customer1.start();
        customer2.start();

        // Wait for both threads to finish
        customer1.join();
        customer2.join();

        // Verify the final state of the car
        Car finalCar = carRepository.findById(2).orElseThrow(() -> new RuntimeException("Car not found"));
        assertNotNull(finalCar);
        // Assert that the final car details are one of the expected states
        assertTrue(finalCar.getPrice() == 19000.00 || finalCar.getPrice() == 18000.00);
    }
}