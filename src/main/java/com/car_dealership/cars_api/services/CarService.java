package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.color.Color;
import com.car_dealership.cars_api.models.car.CarRequestDTO;
import com.car_dealership.cars_api.models.car.CarResponseDTO;
import com.car_dealership.cars_api.models.color.ColorRequestDTO;
import com.car_dealership.cars_api.models.manufacturer.Manufacturer;
import com.car_dealership.cars_api.repositories.CarRepository;
import com.car_dealership.cars_api.repositories.ColorRepository;
import com.car_dealership.cars_api.repositories.ManufacturerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<CarResponseDTO> getAllCars() {
        List<Car> allCars = carRepository.findAll();
        return allCars.stream().map(car -> new CarResponseDTO(
                car.getCar_id(),
                car.getCar_name(),
                car.getBrand(),
                car.getModel(),
                car.getRelease_year(),
                car.getMotor(),
                car.getKilometers(),
                car.getPrice(),
                car.getManufacturer(),
                car.getColors()
                        .stream()
                        .map( Color::getColor_name )
                        .collect(Collectors.toSet())) // Adds only the name
        ).toList();
    }

    public Car getCarById(Integer id) {
        Optional<Car> carExists = carRepository.findById(id);
        if (carExists.isPresent()) {
            return carExists.get();
        }
        System.out.println("Car with id { " + id + " } was not found");
        return null;
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
        ManufacturerRepository.
        Optional<Car> carExists = carRepository.findByCarName(carRequest.car_name());
        if (carExists.isEmpty()) {
            Car newCar = new Car(
                    carRequest.car_name(),
                    carRequest.brand(),
                    carRequest.model(),
                    carRequest.release_year(),
                    carRequest.motor(),
                    carRequest.kilometers(),
                    carRequest.price(),
                    carRequest.manufacturer(),
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
        return carRepository.save(car);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
