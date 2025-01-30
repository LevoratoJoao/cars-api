package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.sales.SalesRequestDTO;
import com.car_dealership.cars_api.dto.sales.SalesResponseDTO;
import com.car_dealership.cars_api.models.Car;
import com.car_dealership.cars_api.models.Customer;
import com.car_dealership.cars_api.models.Employee;
import com.car_dealership.cars_api.models.Sales;
import com.car_dealership.cars_api.repositories.CarRepository;
import com.car_dealership.cars_api.repositories.CustomerRepository;
import com.car_dealership.cars_api.repositories.EmployeeRepository;
import com.car_dealership.cars_api.repositories.SalesRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class SalesService {

    private @NonNull SalesRepository salesRepository;

    // TODO: Remove all these repositories and make the getById methods return the actual model
    private @NonNull CarRepository carRepository;
    private @NonNull CustomerRepository customerRepository;
    private @NonNull EmployeeRepository employeeRepository;

    public List<SalesResponseDTO> getAllSales() {
        return salesRepository.findAll()
                .stream()
                .map(sale -> new SalesResponseDTO(
                        sale.getSales_id(),
                        sale.getSale_date(),
                        sale.getSale_price(),
                        sale.getCar(),
                        sale.getCustomer(),
                        sale.getEmployee())
                ).toList();
    }

    public SalesResponseDTO getSaleById(Integer id) {
        Optional<Sales> sale = salesRepository.findById(id);
        if (sale.isPresent()) {
            return new SalesResponseDTO(
                    sale.get().getSales_id(),
                    sale.get().getSale_date(),
                    sale.get().getSale_price(),
                    sale.get().getCar(),
                    sale.get().getCustomer(),
                    sale.get().getEmployee()
            );
        }
        System.out.println("Sale with id { " + id + " } was not found");
        return null;
    }

    public SalesResponseDTO saveSale(SalesRequestDTO sale) {

        Optional<Car> car = carRepository.findById(sale.car_id());
        if (car.isEmpty()) {
            System.out.println("Car with id { " + sale.car_id() + " } was not found");
            return null;
        }

        Optional<Employee> employee = employeeRepository.findById(sale.employee_id());
        if (employee.isEmpty()) {
            System.out.println("Employee with id { " + sale.employee_id() + " } was not found");
            return null;
        }

        Optional<Customer> customer = customerRepository.findById(sale.customer_id());
        if (customer.isEmpty()) {
            System.out.println("Customer with id { " + sale.customer_id() + " } was not found");
            return null;
        }

        Sales newSale = new Sales(sale.date(), sale.sale_price(), customer.get(), car.get(), employee.get());
        salesRepository.save(newSale);
        return new SalesResponseDTO(newSale.getSales_id(), newSale.getSale_date(), newSale.getSale_price(), newSale.getCar(), newSale.getCustomer(), newSale.getEmployee());
    }

}
