package com.car_dealership.cars_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.car_dealership.cars_api.dto.car.CarResponseDTO;

import com.car_dealership.cars_api.repositories.CarRepository;
import com.car_dealership.cars_api.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class CarServiceIntegrationTest {

    @Autowired
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    private AsyncTaskExecutor taskExecutor;

    @Test
    public void testFindAllCarsAsync() throws Exception {

        CompletableFuture<List<CarResponseDTO>> allCarsFuture = carService.getAllCars(0, 5);
        CompletableFuture<CarResponseDTO> carByIdFuture = carService.getCarById(2);

        // Wait for the async task to complete and get the result
        List<CarResponseDTO> allCars = allCarsFuture.join();
        CarResponseDTO carById = carByIdFuture.join();


        assertNotNull(allCars);
        assertFalse(allCars.isEmpty());
        assertNotNull(carById);
        assertEquals("Toyota Corolla", carById.car_name());
    }

    @Test
    public void testFindCarByIdAsync() throws Exception {
        CompletableFuture<CarResponseDTO> future = carService.getCarById(2);

        // Allow some time for async task to complete
        while (!future.isDone()) {
            Thread.sleep(100);
        }

        CarResponseDTO car = future.get();
        assertNotNull(car);
        assertEquals("Toyota Corolla", car.car_name());
    }
}

