package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.car.CarRequestDTO;
import com.car_dealership.cars_api.dto.car.CarResponseDTO;
import com.car_dealership.cars_api.dto.sales.SalesRequestDTO;
import com.car_dealership.cars_api.dto.sales.SalesResponseDTO;
import com.car_dealership.cars_api.models.*;
import com.car_dealership.cars_api.repositories.CustomerRepository;
import com.car_dealership.cars_api.repositories.EmployeeRepository;
import com.car_dealership.cars_api.repositories.SalesRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SalesService {

    private @NonNull SalesRepository salesRepository;

    // TODO: Remove all these repositories and make the getById methods return the actual model
    private @NonNull CarService carService;
    private @NonNull CustomerRepository customerRepository;
    private @NonNull EmployeeRepository employeeRepository;

    @Async
    public CompletableFuture<List<SalesResponseDTO>> getAllSales() {
        return CompletableFuture.completedFuture(salesRepository.findAll()
                .stream()
                .map(sale -> new SalesResponseDTO(
                        sale.getSales_id(),
                        sale.getSale_date(),
                        sale.getSale_price(),
                        sale.getCar(),
                        sale.getCustomer(),
                        sale.getEmployee())
                ).toList());
    }

    @Async
    public CompletableFuture<SalesResponseDTO> getSaleById(Integer id) {
        Optional<Sales> sale = salesRepository.findById(id);
        if (sale.isPresent()) {
            return CompletableFuture.completedFuture(new SalesResponseDTO(
                    sale.get().getSales_id(),
                    sale.get().getSale_date(),
                    sale.get().getSale_price(),
                    sale.get().getCar(),
                    sale.get().getCustomer(),
                    sale.get().getEmployee()
            ));
        }
        System.out.println("Sale with id { " + id + " } was not found");
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional
    public CompletableFuture<SalesResponseDTO> saveSale(SalesRequestDTO sale)  {
        Optional<Employee> employee = employeeRepository.findById(sale.employee_id());
        if (employee.isEmpty()) {
            System.out.println("Employee with id { " + sale.employee_id() + " } was not found");
            return CompletableFuture.completedFuture(null);
        }

        Optional<Customer> customer = customerRepository.findById(sale.customer_id());
        if (customer.isEmpty()) {
            System.out.println("Customer with id { " + sale.customer_id() + " } was not found");
            return CompletableFuture.completedFuture(null);
        }

        try {
            CompletableFuture<CarResponseDTO> car = carService.getCarById(sale.car_id());
            CarResponseDTO carResponseDTO = car.get();

            if (carResponseDTO.sold()) {
                System.out.println("Car with id { " + sale.car_id() + " } was already sold");
                return CompletableFuture.completedFuture(null);
            }

            Car updatedCar = new Car(carResponseDTO.car_id(),
                    carResponseDTO.car_name(),
                    carResponseDTO.model(),
                    carResponseDTO.release_year(),
                    carResponseDTO.motor(),
                    carResponseDTO.kilometers(),
                    carResponseDTO.price(),
                    true,
                    new Manufacturer(
                            carResponseDTO.manufacturer().man_name(),
                            carResponseDTO.manufacturer().country()
                    ),
                    carResponseDTO.colors()
                            .stream()
                            .map(Color::new).collect(Collectors.toSet()));

            Sales newSale = new Sales(sale.date(), sale.sale_price(), customer.get(), updatedCar, employee.get());
            salesRepository.save(newSale);

            carService.updateCar(carResponseDTO.car_id(), new CarRequestDTO(
                    carResponseDTO.car_name(),
                    carResponseDTO.model(),
                    carResponseDTO.release_year(),
                    carResponseDTO.motor(),
                    carResponseDTO.kilometers(),
                    carResponseDTO.price(),
                    true,
                    carResponseDTO.manufacturer(),
                    carResponseDTO.colors())
            );

            return CompletableFuture.completedFuture(new SalesResponseDTO(newSale.getSales_id(), newSale.getSale_date(), newSale.getSale_price(), newSale.getCar(), newSale.getCustomer(), newSale.getEmployee()));
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Car with id { " + sale.car_id() + " } was not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<List<SalesResponseDTO>> getFilteredSales(Integer page, Integer size, LocalDate date, Float min_price, Float max_price, String car_name, String customer_name, String employee_name) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        //Pageable pageable = PageRequest.of(page, size);

        Page<Sales> filteredSales = salesRepository.findFilteredSales(pageable, date, min_price, max_price, car_name, customer_name, employee_name);

        return CompletableFuture.completedFuture(filteredSales.stream()
                .map(sale -> new SalesResponseDTO(
                                sale.getSales_id(),
                                sale.getSale_date(),
                                sale.getSale_price(),
                                sale.getCar(),
                                sale.getCustomer(),
                                sale.getEmployee()
                        )
                ).toList());
    }

}
