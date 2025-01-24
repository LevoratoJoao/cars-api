package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.employee.EmployeeRequestDTO;
import com.car_dealership.cars_api.dto.employee.EmployeeResponseDTO;
import com.car_dealership.cars_api.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> post(@RequestBody EmployeeRequestDTO employeeRequest) {
        return ResponseEntity.ok().body(employeeService.saveEmployee(employeeRequest));
    }

}
