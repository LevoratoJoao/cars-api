package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.models.manufacturer.Manufacturer;
import com.car_dealership.cars_api.models.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.models.manufacturer.ManufacturerResponseDTO;
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
        return ResponseEntity.ok().body(manufacturerService.getAllManufacturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok().body(manufacturerService.getManufacturerById(id));
    }

    @PostMapping
    public ResponseEntity<Manufacturer> post(@RequestBody ManufacturerRequestDTO manufacturerRequest) {
        return ResponseEntity.ok().body(manufacturerService.saveManufacturer(manufacturerRequest));
    }
}
