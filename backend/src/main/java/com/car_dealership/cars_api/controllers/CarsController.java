package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.models.Car;
import com.car_dealership.cars_api.dto.car.CarRequestDTO;
import com.car_dealership.cars_api.dto.car.CarResponseDTO;
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
    public ResponseEntity<List<CarResponseDTO>> getAllCars(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) throws InterruptedException {
        List<CarResponseDTO> allCars = carService.getAllCars(page - 1, size);
        return ResponseEntity.ok().body(allCars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(carService.getCarById(id));
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
    public ResponseEntity<CarResponseDTO> put(@PathVariable Integer id, @RequestBody CarRequestDTO carRequest) {
        return ResponseEntity.ok().body(carService.updateCar(id, carRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.lang.String> delete(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car with id: { "+ id +" } was deleted");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CarResponseDTO>> getCarsByFilter(@RequestParam(defaultValue = "1") int page,
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
