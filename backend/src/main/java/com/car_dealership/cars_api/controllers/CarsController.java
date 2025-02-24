package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.car.CarRequestDTO;
import com.car_dealership.cars_api.dto.car.CarResponseDTO;
import com.car_dealership.cars_api.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarsController {
    private final CarService carService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<CarResponseDTO>>> getAllCars(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) throws InterruptedException {
        CompletableFuture<List<CarResponseDTO>> allCars = carService.getAllCars(page - 1, size);
        System.out.println("All cars were found 2");
        return allCars.thenApply(ResponseEntity::ok).exceptionally(ex -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<CarResponseDTO>> getCarById(@PathVariable Integer id) throws InterruptedException {
        return carService.getCarById(id).thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> postNewCar(@RequestBody CarRequestDTO carRequest) {
        return ResponseEntity.ok().body(carService.saveCar(carRequest));
    }

    @PostMapping("/list")
    public ResponseEntity<List<CarResponseDTO>> postListCars(@RequestBody List<CarRequestDTO> carRequest) {
        return ResponseEntity.ok().body(carService.saveCars(carRequest));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<CarResponseDTO>> put(@PathVariable Integer id, @RequestBody CarRequestDTO carRequest) throws Exception {
        return carService.updateCar(id, carRequest).thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.lang.String> delete(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car with id: { "+ id +" } was deleted");
    }

    @GetMapping("/filter")
    public CompletableFuture<ResponseEntity<List<CarResponseDTO>>> getCarsByFilter(@RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "") String manufacturer,
                                                                @RequestParam(defaultValue = "") String model,
                                                                @RequestParam(defaultValue = "") String motor,
                                                                @RequestParam(defaultValue = "0") Integer release_year,
                                                                @RequestParam(defaultValue = "0") Float min_price,
                                                                @RequestParam(defaultValue = "10000000") Float max_price,
                                                                @RequestParam(defaultValue = "") String color,
                                                                @RequestParam(defaultValue = "") String car) {
        return ResponseEntity.ok().body(carService.getFilteredCars(page - 1, size, manufacturer, model, motor, release_year, min_price, max_price, color, car));
    }

    @GetMapping("/length")
    public ResponseEntity<Integer> getLength() {
        return ResponseEntity.ok().body(carService.getLength());
    }
}
