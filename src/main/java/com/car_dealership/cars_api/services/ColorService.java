package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.color.Color;
import com.car_dealership.cars_api.models.color.ColorRequestDTO;
import com.car_dealership.cars_api.models.color.ColorResponseDTO;
import com.car_dealership.cars_api.repositories.ColorRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class ColorService {

    private @NonNull ColorRepository colorRepository;

    public List<ColorResponseDTO> getAllColors() {
        List<Color> allColors = colorRepository.findAll();
        return allColors.stream().map(color -> new ColorResponseDTO(color.getColor_id(), color.getColor_name())).toList();
    }

    public Color saveColor(ColorRequestDTO color) {
        Color newColor = new Color(color.name());
        colorRepository.save(newColor);
        return newColor;
    }
}
