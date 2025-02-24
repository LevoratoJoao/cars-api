package com.car_dealership.cars_api.services;

import com.car_dealership.cars_api.models.Color;
import com.car_dealership.cars_api.dto.color.ColorRequestDTO;
import com.car_dealership.cars_api.dto.color.ColorResponseDTO;
import com.car_dealership.cars_api.repositories.ColorRepository;
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
public class ColorService {

    private @NonNull ColorRepository colorRepository;

    @Async
    public CompletableFuture<List<ColorResponseDTO>> getAllColors() {
        List<Color> allColors = colorRepository.findAll();
        return CompletableFuture.completedFuture(allColors.stream().map(color -> new ColorResponseDTO(color.getColor_id(), color.getColor_name())).toList());
    }

    @Async
    public CompletableFuture<ColorResponseDTO> getColorById(Integer id) {
        Optional<Color> colorExists = colorRepository.findById(id);
        if (colorExists.isPresent()) {
            return CompletableFuture.completedFuture(new ColorResponseDTO(
                    colorExists.get().getColor_id(),
                    colorExists.get().getColor_name()
            ));
        }
        System.out.println("Color with id { " + id + " } was not found");
        return CompletableFuture.completedFuture(null);
    }

    public ColorResponseDTO saveColor(ColorRequestDTO color) {
        Color newColor = new Color(color.name());
        colorRepository.save(newColor);
        return new ColorResponseDTO(newColor.getColor_id(), newColor.getColor_name());
    }
}
