package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.models.Manufacturer;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerResponseDTO;
import com.car_dealership.cars_api.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping()
    public ResponseEntity<List<ManufacturerResponseDTO>> getAllManufacturers() {
        return manufacturerService.getAllManufacturers().thenApply(ResponseEntity::ok).join();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDTO> getById(@PathVariable Integer id) {
        return manufacturerService.getManufacturerById(id).thenApply(ResponseEntity::ok).join();
    }

    @PostMapping
    public ResponseEntity<Manufacturer> post(@RequestBody ManufacturerRequestDTO manufacturerRequest) {
        return ResponseEntity.ok().body(manufacturerService.saveManufacturer(manufacturerRequest));
    }
}
