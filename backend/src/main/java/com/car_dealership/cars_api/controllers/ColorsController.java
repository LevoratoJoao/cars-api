package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.color.ColorRequestDTO;
import com.car_dealership.cars_api.dto.color.ColorResponseDTO;
import com.car_dealership.cars_api.models.Color;
import com.car_dealership.cars_api.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/colors")
public class ColorsController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorResponseDTO>> get() {
        return colorService.getAllColors().thenApply(ResponseEntity::ok).join();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorResponseDTO> get(@PathVariable Integer id) {
        return colorService.getColorById(id).thenApply(ResponseEntity::ok).join();
    }

    @PostMapping
    public ResponseEntity<Color> post(@RequestBody ColorRequestDTO colorRequest) {
        return ResponseEntity.ok().body(colorService.saveColor(colorRequest));
    }
}
