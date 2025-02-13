package com.car_dealership.cars_api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.car_dealership.cars_api.dto.car.CarResponseDTO;

import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
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

        List<CarResponseDTO> allCarsFuture = carService.getAllCars(0, 5);
        CarResponseDTO carByIdFuture = carService.getCarById(2);

        assertNotNull(allCarsFuture);
        assertFalse(allCarsFuture.isEmpty());
        assertNotNull(carByIdFuture);
        assertEquals("Toyota Corolla", carByIdFuture.car_name());
    }

    @Test
    public void testAsyncMethods() throws Exception {
        final List[] allCar = new List[1];
        final EmployeeResponseDTO[] employee = new EmployeeResponseDTO[1];
        final CarResponseDTO[] carById = new CarResponseDTO[1];


        Thread customer1 = new Thread(() -> {
            try {
                List<CarResponseDTO> allCarsFuture = carService.getAllCars(0, 5);
                allCar[0] = allCarsFuture;
            } catch (Exception e) {
                System.out.println("Customer 1: " + e.getMessage());
            }
        });
        Thread customer2 = new Thread(() -> {
            try {
                List<EmployeeResponseDTO> employeeFuture = employeeService.getAllEmployees();
                employee[0] = employeeFuture.getFirst();
            } catch (Exception e) {
                System.out.println("Customer 2: " + e.getMessage());
            }
        });
        Thread customer3 = new Thread(() -> {
            try {
                CarResponseDTO carByIdFuture = carService.getCarById(2);
                carById[0] = carByIdFuture;
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
}
