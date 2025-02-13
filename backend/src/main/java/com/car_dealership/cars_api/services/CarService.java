package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.dto.color.ColorResponseDTO;
import com.car_dealership.cars_api.models.Car;
import com.car_dealership.cars_api.models.Color;
import com.car_dealership.cars_api.dto.car.CarRequestDTO;
import com.car_dealership.cars_api.dto.car.CarResponseDTO;
import com.car_dealership.cars_api.dto.color.ColorRequestDTO;
import com.car_dealership.cars_api.models.Manufacturer;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.repositories.CarRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
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
                car.getSold(),
                new ManufacturerRequestDTO(car.getManufacturer().getManufacturer_name(), car.getManufacturer().getCountry()),
                car.getColors()
                        .stream()
                        .map( Color::getColor_name )
                        .collect(Collectors.toSet())
        );
    }

    @Cacheable("cars")
    @Async
    public CompletableFuture<List<CarResponseDTO>> getAllCars(int page, int size) throws InterruptedException {

        System.out.println("Executing getAllCars in thread: " + Thread.currentThread().getName());
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> allCars = carRepository.findAll(pageable);
        Thread.sleep(10000);
        System.out.println("All cars were found in thread: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(allCars.stream().map(this::createNewCarResponse).toList());
    }

    @Async
    public CompletableFuture<CarResponseDTO> getCarById(Integer id) throws InterruptedException {
        System.out.println("Executing getCarById in thread: " + Thread.currentThread().getName());

        Optional<Car> carExists = carRepository.findById(id);
        Thread.sleep(2000);
        if (carExists.isPresent()) {
            System.out.println("Car with id { " + id + " } was found in thread: " + Thread.currentThread().getName());
            return CompletableFuture.completedFuture(createNewCarResponse(carExists.get()));
        }
        System.out.println("Car with id { " + id + " } was not found in thread: " + Thread.currentThread().getName());

        return CompletableFuture.completedFuture(null);
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

    public Set<Color> getColorsOrCreate(CarRequestDTO carRequest) {
        return carRequest.colors().stream().map(color -> colorService
                .getColorRepository()
                .findByColorName(color)
                .orElseGet(() -> {
                    ColorResponseDTO newColor = colorService.saveColor(new ColorRequestDTO(color));
                    System.out.println("New color was added: " + newColor.name());
                    return new Color(newColor.name());
                })).collect(Collectors.toSet());
    }

    public CarResponseDTO saveCar(CarRequestDTO carRequest) {
        Set<Color> colors = getColorsOrCreate(carRequest);
        Car newCar = new Car(
                carRequest.car_name(),
                carRequest.model(),
                carRequest.release_year(),
                carRequest.motor(),
                carRequest.kilometers(),
                carRequest.price(),
                carRequest.sold(),
                getManufacturerOrCreate(carRequest),
                colors
        );
        carRepository.save(newCar);
        return createNewCarResponse(newCar);
    }


    public List<CarResponseDTO> saveCars(List<CarRequestDTO> carRequest) {
        List<CarResponseDTO> response = new ArrayList<>();
        for (CarRequestDTO car : carRequest) {
            response.add(saveCar(car));
        }
        return response;
    }

    @Transactional
    @Async
    public CompletableFuture<CarResponseDTO> updateCar(Integer carId, CarRequestDTO updateCar) throws Exception {
        try {
            System.out.println("Executing updateCar in thread: " + Thread.currentThread().getName());
            Car car = carRepository.findById(carId).orElseThrow(() -> new NoSuchElementException("Car not found"));

            car.setCar_name(updateCar.car_name());
            car.setModel(updateCar.model());
            car.setRelease_year(updateCar.release_year());
            car.setMotor(updateCar.motor());
            car.setKilometers(updateCar.kilometers());
            car.setPrice(updateCar.price());

            Set<Color> colors = getColorsOrCreate(updateCar);
            car.setColors(colors);

            carRepository.save(car);

            return CompletableFuture.completedFuture(
                    new CarResponseDTO(car.getCar_id(),
                            car.getCar_name(),
                            car.getModel(),
                            car.getRelease_year(),
                            car.getMotor(),
                            car.getKilometers(),
                            car.getPrice(),
                            car.getSold(),
                            new ManufacturerRequestDTO(
                                    car.getManufacturer().getManufacturer_name(),
                                    car.getManufacturer().getCountry()
                            ),
                            car.getColors().stream().map(Color::getColor_name).collect(Collectors.toSet())
                    )
            );
        } catch (Exception e) {
            throw new Exception("The car was updated by another user. Please reload and try again.");
        }
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

    @Async
    public CompletableFuture<List<CarResponseDTO>> getFilteredCars(Integer page,
                                                                   Integer size,
                                                                   String manufacturer,
                                                                   String model,
                                                                   String motor,
                                                                   Integer release_year,
                                                                   Float min_price,
                                                                   Float max_price,
                                                                   String color,
                                                                   String car) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> allCars = carRepository.findFilteredCars(pageable, manufacturer, model, motor, release_year, min_price, max_price, color, car);
        return CompletableFuture.completedFuture(allCars.stream().map(this::createNewCarResponse).toList());
    }

    public Integer getLength() {
        return (int) carRepository.count();
    }
}
