package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.car.CarRequestDTO;
import com.car_dealership.cars_api.models.car.CarResponseDTO;
import com.car_dealership.cars_api.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarsController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarResponseDTO>> getAllCars() {
        return ResponseEntity.ok().body(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> get(@PathVariable Integer id) {
        return ResponseEntity.ok().body(carService.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<Car> post(@RequestBody CarRequestDTO carRequest) {
        return ResponseEntity.ok().body(carService.saveCar(carRequest));
    }

    @PutMapping
    public ResponseEntity<Car> put(@RequestBody Car carRequest) {
        return ResponseEntity.ok().body(carService.updateCar(carRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.lang.String> delete(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car with id: { "+ id +" } was deleted");
    }
}
