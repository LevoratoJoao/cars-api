package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.manufacturer.Manufacturer;
import com.car_dealership.cars_api.models.manufacturer.ManufacturerRequestDTO;
import com.car_dealership.cars_api.models.manufacturer.ManufacturerResponseDTO;
import com.car_dealership.cars_api.repositories.ManufacturerRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ManufacturerService {
    private @NonNull ManufacturerRepository manufacturerRepository;

    public Manufacturer saveManufacturer(ManufacturerRequestDTO manufacturerRequest) {
        Manufacturer newManufacturer = new Manufacturer(manufacturerRequest.man_name(), manufacturerRequest.country());
        return manufacturerRepository.save(newManufacturer);
    }
}
