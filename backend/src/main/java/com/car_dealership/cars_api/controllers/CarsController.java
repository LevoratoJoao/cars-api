package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.car.CarRequestDTO;
import com.car_dealership.cars_api.models.car.CarResponseDTO;
import com.car_dealership.cars_api.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<CarResponseDTO> get(@PathVariable Integer id) {
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

    @GetMapping("/filter")
    public ResponseEntity<List<CarResponseDTO>> getCarsByManufacturer(@RequestParam(defaultValue = "") String manufacturer,
                                                                      @RequestParam(defaultValue = "") String model,
                                                                      @RequestParam(defaultValue = "") String motor,
                                                                      @RequestParam(defaultValue = "0") Integer release_year,
                                                                      @RequestParam(defaultValue = "0") Float min_price,
                                                                      @RequestParam(defaultValue = "10000000") Float max_price,
                                                                      @RequestParam(defaultValue = "") String color,
                                                                      @RequestParam(defaultValue = "") String car) {
        return ResponseEntity.ok().body(carService.getFilteredCars(manufacturer, model, motor, release_year, min_price, max_price, color, car));
    }
}
