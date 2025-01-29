package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.sales.SalesRequestDTO;
import com.car_dealership.cars_api.dto.sales.SalesResponseDTO;
import com.car_dealership.cars_api.services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<SalesResponseDTO> put(@RequestBody SalesRequestDTO salesRequest) {
        return ResponseEntity.ok().body(salesService.saveSale(salesRequest));
    }
}
