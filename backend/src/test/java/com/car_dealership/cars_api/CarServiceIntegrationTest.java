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

        List<CarResponseDTO> allCarsFuture = carService.getAllCars(0, 5);
        CarResponseDTO carByIdFuture = carService.getCarById(2);

        // Wait for the async task to complete and get the result


        assertNotNull(allCarsFuture);
        assertFalse(allCarsFuture.isEmpty());
        assertNotNull(carByIdFuture);
        assertEquals("Toyota Corolla", carByIdFuture.car_name());
    }
}
