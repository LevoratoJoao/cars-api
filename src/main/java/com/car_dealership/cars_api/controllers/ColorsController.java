package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.models.color.Color;
import com.car_dealership.cars_api.models.color.ColorRequestDTO;
import com.car_dealership.cars_api.models.color.ColorResponseDTO;
import com.car_dealership.cars_api.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/colors")
public class ColorsController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorResponseDTO>> get() {
        return ResponseEntity.ok().body(colorService.getAllColors());
    }

    @PostMapping
    public ResponseEntity<Color> post(@RequestBody ColorRequestDTO colorRequest) {
        return ResponseEntity.ok().body(colorService.saveColor(colorRequest));
    }
}
