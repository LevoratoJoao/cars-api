package com.car_dealership.cars_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.car_dealership.cars_api.dto.car.CarRequestDTO;
import com.car_dealership.cars_api.dto.car.CarResponseDTO;

import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.repositories.CarRepository;
import com.car_dealership.cars_api.services.CarService;
import com.car_dealership.cars_api.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class CarServiceIntegrationTest {

    @Autowired
    private CarService carService;

    @Autowired
    private EmployeeService employeeService;

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
    public void testAsyncMethods() throws Exception {
        final List[] allCar = new List[1];
        final EmployeeResponseDTO[] employee = new EmployeeResponseDTO[1];
        final CarResponseDTO[] carById = new CarResponseDTO[1];


        Thread customer1 = new Thread(() -> {
            try {
                CompletableFuture<List<CarResponseDTO>> allCarsFuture = carService.getAllCars(0, 5);
                allCar[0] = allCarsFuture.join();
            } catch (Exception e) {
                System.out.println("Customer 1: " + e.getMessage());
            }
        });
        Thread customer2 = new Thread(() -> {
            try {
                CompletableFuture<List<EmployeeResponseDTO>> employeeFuture = employeeService.getAllEmployees();
                employee[0] = employeeFuture.join().getFirst();
            } catch (Exception e) {
                System.out.println("Customer 2: " + e.getMessage());
            }
        });
        Thread customer3 = new Thread(() -> {
            try {
                CompletableFuture<CarResponseDTO> carByIdFuture = carService.getCarById(2);
                carById[0] = carByIdFuture.join();
            } catch (Exception e) {
                System.out.println("Customer 3: " + e.getMessage());
            }
        });

        customer1.start();
        customer2.start();
        customer3.start();

        customer1.join();
        customer2.join();
        customer3.join();

        assertNotNull(allCar[0]);
        assertFalse(allCar[0].isEmpty());
        assertNotNull(employee[0]);
        assertNotNull(carById[0]);
        assertEquals("Toyota Corolla", carById[0].car_name());
    }

    @Test
    public void testAsyncQueue() throws Exception {
        Thread[] testThreads = new Thread[15];

        for (int i = 0; i < 15; i++) {
            testThreads[i] = new Thread(() -> {
                try {
                    CompletableFuture<List<CarResponseDTO>> allCarsFuture = carService.getAllCars(0, 5);
                    List<CarResponseDTO> allCars = allCarsFuture.join();
                    assertNotNull(allCars);
                    assertFalse(allCars.isEmpty());
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            });
        }

        for (Thread thread : testThreads) {
            System.out.println("Starting thread: " + thread.getName());
            thread.start();
        }

        for (Thread thread : testThreads) {
            System.out.println("Joining thread: " + thread.getName());
            thread.join();
        }

    }
}

