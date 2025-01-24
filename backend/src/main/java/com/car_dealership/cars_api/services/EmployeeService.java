package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.employee.EmployeeRequestDTO;
import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
import com.car_dealership.cars_api.models.employee.Employee;
import com.car_dealership.cars_api.repositories.EmployeeRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeService {
    private @NonNull EmployeeRepository employeeRepository;

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees.stream().map(employee -> new EmployeeResponseDTO(employee.getEmployee_id(), employee.getFirst_name(), employee.getLast_name(), employee.getPosition(), employee.getSalary(), employee.getHire_date(), employee.getPhone_number(), employee.getEmail())).toList();
    }

    public EmployeeResponseDTO getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return new EmployeeResponseDTO(employee.get().getEmployee_id(), employee.get().getFirst_name(), employee.get().getLast_name(), employee.get().getPosition(), employee.get().getSalary(), employee.get().getHire_date(), employee.get().getPhone_number(), employee.get().getEmail());
        }
        System.out.println("Employee with id { " + id + " } was not found");
        return null;
    }

    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employee) {
        Employee newEmployee = new Employee(employee.first_name(), employee.last_name(), employee.position(), employee.salary(), employee.hire_date(), employee.phone_number(), employee.email());
        employeeRepository.save(newEmployee);
        return new EmployeeResponseDTO(newEmployee.getEmployee_id(), newEmployee.getFirst_name(), newEmployee.getLast_name(), newEmployee.getPosition(), newEmployee.getSalary(), newEmployee.getHire_date(), newEmployee.getPhone_number(), newEmployee.getEmail());
    }
}
