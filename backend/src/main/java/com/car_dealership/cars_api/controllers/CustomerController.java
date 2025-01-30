package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.customer.CustomerRequestDTO;
import com.car_dealership.cars_api.dto.customer.CustomerResponseDTO;
import com.car_dealership.cars_api.services.CustomerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private @NonNull final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> postEmployee(@RequestBody CustomerRequestDTO customerRequest) {
        return ResponseEntity.ok().body(customerService.saveCustomer(customerRequest));
    }

    @PostMapping("/list")
    public ResponseEntity<List<CustomerResponseDTO>> postListEmployees(@RequestBody List<CustomerRequestDTO> customersRequest) {
        return ResponseEntity.ok().body(customerService.saveCustomers(customersRequest));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomersByFilter(@RequestParam(defaultValue = "1") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          @RequestParam(defaultValue = "") String first_name,
                                                                          @RequestParam(defaultValue = "") String last_name,
                                                                          @RequestParam(defaultValue = "") String email,
                                                                          @RequestParam(defaultValue = "") String phone_number) {
        return ResponseEntity.ok().body(customerService.getFilteredCustomers(page - 1, size, first_name, last_name, email, phone_number));
    }
}
