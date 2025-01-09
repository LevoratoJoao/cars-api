package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.Color;
import com.car_dealership.cars_api.models.car.CarRequestDTO;
import com.car_dealership.cars_api.models.car.CarResponseDTO;
import com.car_dealership.cars_api.repositories.CarRepository;
import com.car_dealership.cars_api.repositories.ColorRepository;
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
    private @NonNull CarRepository carRepository;
    private @NonNull ColorRepository colorRepository;

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
                car.getColors().stream().map( Color::getColor_name ).collect(Collectors.toSet())) // Adds only the name
        ).toList();
    }

    public Car getCarById(Integer id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        }
        System.out.println("Car with id { " + id + " } was not found");
        return null;
    }

    public Car saveCar(CarRequestDTO carRequest) {
        Set<Color> colors = carRequest.colors().stream().map(color -> colorRepository
            .findColorByColorName(color)
            .orElseGet(() -> {
                Color newColor = colorService.saveColor(new Color(color));
                System.out.println("New color was added: " + newColor.getColor_name());
                return newColor;
            })).collect(Collectors.toSet());
        Car newCar = new Car(
                carRequest.car_name(),
                carRequest.brand(),
                carRequest.model(),
                carRequest.release_year(),
                carRequest.motor(),
                carRequest.kilometers(),
                carRequest.price(),
                colors
        );
        carRepository.save(newCar);
        return newCar;
    }

    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
