package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.employee.EmployeeRequestDTO;
import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
import com.car_dealership.cars_api.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return employeeService.getAllEmployees().thenApply(ResponseEntity::ok).join();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id).thenApply(ResponseEntity::ok).join();
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> post(@RequestBody EmployeeRequestDTO employeeRequest) {
        return ResponseEntity.ok().body(employeeService.saveEmployee(employeeRequest));
    }

    @PostMapping("/list")
    public ResponseEntity<List<EmployeeResponseDTO>> postListEmployees(@RequestBody List<EmployeeRequestDTO> employeesRequest) {
        return ResponseEntity.ok().body(employeeService.saveEmployees(employeesRequest));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByFilter(@RequestParam(defaultValue = "1") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          @RequestParam(defaultValue = "") String first_name,
                                                                          @RequestParam(defaultValue = "") String last_name,
                                                                          @RequestParam(defaultValue = "") String position,
                                                                          @RequestParam(defaultValue = "") String email,
                                                                          @RequestParam(defaultValue = "0") Float min_salary,
                                                                          @RequestParam(defaultValue = "10000000") Float max_salary,
                                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                             LocalDate hire_date,
                                                                          @RequestParam(defaultValue = "") String phone_number) {
    return employeeService.getFilteredEmployees(page - 1, size, first_name, last_name, position, email, min_salary, max_salary, hire_date, phone_number).thenApply(ResponseEntity::ok).join();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> putEmployee(@PathVariable Integer id, @RequestBody EmployeeRequestDTO employeeRequest) {
        return employeeService.updateEmployee(id, employeeRequest).thenApply(ResponseEntity::ok).join();
    }

}
