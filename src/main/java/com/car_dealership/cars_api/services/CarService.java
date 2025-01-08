package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.Car;
import com.car_dealership.cars_api.repositories.CarRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //don't know why lombok is not working
public class CarService {

    private @NonNull CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Integer id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        }
        System.out.println("Car with id { " + id + " } was not found");
        return null;
    }

    public Car saveCar(Car car) {
        Car newCar = new Car(car.getCar_name(), car.getBrand(), car.getModel(), car.getRelease_year(), car.getColor(), car.getMotor(), car.getKilometers(), car.getPrice());
        carRepository.save(newCar);
        return newCar;
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
