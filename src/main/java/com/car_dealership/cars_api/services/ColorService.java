package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.Color;
import com.car_dealership.cars_api.repositories.ColorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {

    private @NonNull ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public Color saveColor(Color color) {
        Color newColor = new Color(color.getColor_name());
        colorRepository.save(newColor);
        return newColor;
    }
}
