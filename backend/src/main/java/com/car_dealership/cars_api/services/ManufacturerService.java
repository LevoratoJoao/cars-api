package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.Manufacturer;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.dto.manufacturer.ManufacturerResponseDTO;
import com.car_dealership.cars_api.repositories.ManufacturerRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ManufacturerService {
    private @NonNull ManufacturerRepository manufacturerRepository;

    public Manufacturer saveManufacturer(ManufacturerRequestDTO manufacturerRequest) {
        Manufacturer newManufacturer = new Manufacturer(manufacturerRequest.man_name(), manufacturerRequest.country());
        manufacturerRepository.save(newManufacturer);
        return newManufacturer;
    }

    @Async
    public CompletableFuture<List<ManufacturerResponseDTO>> getAllManufacturers() {
        List<Manufacturer> allManufacturer = manufacturerRepository.findAll();
        return CompletableFuture.completedFuture(allManufacturer
                .stream()
                .map(manufacturer -> new ManufacturerResponseDTO(
                        manufacturer.getManufacturer_id(),
                        manufacturer.getManufacturer_name(),
                        manufacturer.getCountry())
                ).toList());
    }

    @Async
    public CompletableFuture<ManufacturerResponseDTO> getManufacturerById(Integer id) {
        Optional<Manufacturer> manExists = manufacturerRepository.findById(id);
        if (manExists.isPresent()) {
            return CompletableFuture.completedFuture(new ManufacturerResponseDTO(
                    manExists.get().getManufacturer_id(),
                    manExists.get().getManufacturer_name(),
                    manExists.get().getCountry()
            ));
        }
        System.out.println("Manufacturer with id { " + id + " } was not found");
        return CompletableFuture.completedFuture(null);
    }
}
