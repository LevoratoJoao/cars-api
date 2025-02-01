package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.sales.SalesRequestDTO;
import com.car_dealership.cars_api.dto.sales.SalesResponseDTO;
import com.car_dealership.cars_api.services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SalesService salesService;

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        return ResponseEntity.ok().body(salesService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(salesService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<SalesResponseDTO> post(@RequestBody SalesRequestDTO salesRequest) {
        return ResponseEntity.ok().body(salesService.saveSale(salesRequest));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SalesResponseDTO>> get(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                      @RequestParam(defaultValue = "0") Float min_price,
                                                      @RequestParam(defaultValue = "10000000") Float max_price,
                                                      @RequestParam(defaultValue = "") String car_name,
                                                      @RequestParam(defaultValue = "") String customer_name,
                                                      @RequestParam(defaultValue = "") String employee_name) {
        return ResponseEntity.ok().body(salesService.getFilteredSales(page - 1, size, date, min_price, max_price, car_name, customer_name, employee_name));
    }
}
