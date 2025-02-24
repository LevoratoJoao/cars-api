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
    @Test
    public void testAsyncMethods() throws Exception {
        final List[] allCar = new List[1];
        final EmployeeResponseDTO[] employee = new EmployeeResponseDTO[1];
        final CarResponseDTO[] carById = new CarResponseDTO[1];


        assertNotNull(allCars);
        assertFalse(allCars.isEmpty());
        assertNotNull(carById);
        assertEquals("Toyota Corolla", carById.car_name());
    }
}

