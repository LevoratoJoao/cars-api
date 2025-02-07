package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.employee.EmployeeRequestDTO;
import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
import com.car_dealership.cars_api.models.Employee;
import com.car_dealership.cars_api.repositories.EmployeeRepository;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeService {
    private @NonNull EmployeeRepository employeeRepository;

    @Async
    public CompletableFuture<List<EmployeeResponseDTO>> getAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return CompletableFuture.completedFuture(allEmployees.stream().map(employee -> new EmployeeResponseDTO(employee.getEmployee_id(), employee.getFirst_name(), employee.getLast_name(), employee.getPosition(), employee.getSalary(), employee.getHire_date(), employee.getPhone_number(), employee.getEmail())).toList());
    }

    @Async
    public CompletableFuture<EmployeeResponseDTO> getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return CompletableFuture.completedFuture(new EmployeeResponseDTO(employee.get().getEmployee_id(), employee.get().getFirst_name(), employee.get().getLast_name(), employee.get().getPosition(), employee.get().getSalary(), employee.get().getHire_date(), employee.get().getPhone_number(), employee.get().getEmail()));
        }
        System.out.println("Employee with id { " + id + " } was not found");
        return CompletableFuture.completedFuture(null);
    }

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employee) {
        Employee newEmployee = new Employee(employee.first_name(), employee.last_name(), employee.position(), employee.salary(), employee.hire_date(), employee.phone_number(), employee.email());
        employeeRepository.save(newEmployee);
        return new EmployeeResponseDTO(newEmployee.getEmployee_id(), newEmployee.getFirst_name(), newEmployee.getLast_name(), newEmployee.getPosition(), newEmployee.getSalary(), newEmployee.getHire_date(), newEmployee.getPhone_number(), newEmployee.getEmail());
    }

    public List<EmployeeResponseDTO> saveEmployees(List<EmployeeRequestDTO> employeesRequest) {
        List<EmployeeResponseDTO> customersResponse = new ArrayList<>();
        for (EmployeeRequestDTO employee : employeesRequest) {
            customersResponse.add(saveEmployee(employee));
        }
        return customersResponse;
    }

    @Async
    public CompletableFuture<List<EmployeeResponseDTO>> getFilteredEmployees(Integer page, Integer size, String first_name, String last_name, String position, String email, Float min_salary, Float max_salary, LocalDate hire_date, String phone_number) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> filteredEmployees = employeeRepository.findFilteredEmployees(pageable, first_name, last_name, position, email, min_salary, max_salary, hire_date, phone_number);
        return CompletableFuture.completedFuture(filteredEmployees.stream().map(employee -> new EmployeeResponseDTO(employee.getEmployee_id(), employee.getFirst_name(), employee.getLast_name(), employee.getPosition(), employee.getSalary(), employee.getHire_date(), employee.getPhone_number(), employee.getEmail())).toList());
    }

}
