package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.color.Color;
import com.car_dealership.cars_api.models.car.CarRequestDTO;
import com.car_dealership.cars_api.models.car.CarResponseDTO;
import com.car_dealership.cars_api.models.color.ColorRequestDTO;
import com.car_dealership.cars_api.models.manufacturer.Manufacturer;
import com.car_dealership.cars_api.models.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.repositories.CarRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final ColorService colorService;
    private final ManufacturerService manufacturerService;
    private @NonNull CarRepository carRepository;

    public CarResponseDTO createNewCarResponse(Car car) {
        return new CarResponseDTO(
                car.getCar_id(),
                car.getCar_name(),
                car.getModel(),
                car.getRelease_year(),
                car.getMotor(),
                car.getKilometers(),
                car.getPrice(),
                new ManufacturerRequestDTO(car.getManufacturer().getManufacturer_name(), car.getManufacturer().getCountry()),
                car.getColors()
                        .stream()
                        .map( Color::getColor_name )
                        .collect(Collectors.toSet())
        );
    }

    public List<CarResponseDTO> getAllCars() {
        List<Car> allCars = carRepository.findAll();
        return allCars.stream().map(this::createNewCarResponse).toList();
    }

    public CarResponseDTO getCarById(Integer id) {
        Optional<Car> carExists = carRepository.findById(id);
        if (carExists.isPresent()) {
            return createNewCarResponse(carExists.get());
        }
        System.out.println("Car with id { " + id + " } was not found");
        return null;
    }

    public Manufacturer getManufacturerOrCreate(CarRequestDTO carRequest) {
        return manufacturerService.
                getManufacturerRepository()
                .findByManName(carRequest.manufacturer().man_name())
                .orElseGet(() -> {
                    Manufacturer newManufacturer = manufacturerService.saveManufacturer(new ManufacturerRequestDTO(
                            carRequest.manufacturer().man_name(),
                            carRequest.manufacturer().country()
                    ));
                    System.out.println("New manufacturer was added: " + newManufacturer.getManufacturer_name());
                    return newManufacturer;
                });
    }

    public Car saveCar(CarRequestDTO carRequest) {
        Set<Color> colors = carRequest.colors().stream().map(color -> colorService
            .getColorRepository()
            .findByColorName(color)
            .orElseGet(() -> {
                Color newColor = colorService.saveColor(new ColorRequestDTO(color));
                System.out.println("New color was added: " + newColor.getColor_name());
                return newColor;
            })).collect(Collectors.toSet());
        Optional<Car> carExists = carRepository.findByCarName(carRequest.car_name());
        if (carExists.isEmpty()) {
            Car newCar = new Car(
                    carRequest.car_name(),
                    carRequest.model(),
                    carRequest.release_year(),
                    carRequest.motor(),
                    carRequest.kilometers(),
                    carRequest.price(),
                    getManufacturerOrCreate(carRequest),
                    colors
            );
            carRepository.save(newCar);
            return newCar;
        }
        carExists.get().setColors(colors);
        carRepository.save(carExists.get());
        return carExists.get();
    }

    public Car updateCar(Car car) {
        System.out.println(car.toString());
        return carRepository.save(car);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

    public List<CarResponseDTO> getFilteredCars(String manufacturer,
                                                      String model,
                                                      String motor,
                                                      Integer release_year,
                                                      Float min_price,
                                                      Float max_price,
                                                      String color) {
        List<Car> cars = carRepository.findFilteredCars(manufacturer, model, motor, release_year, min_price, max_price, color);
        return cars.stream().map(this::createNewCarResponse).toList();
    }
}
