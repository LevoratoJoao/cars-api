package com.car_dealership.cars_api.controllers;

import com.car_dealership.cars_api.dto.color.ColorRequestDTO;
import com.car_dealership.cars_api.dto.color.ColorResponseDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<ColorResponseDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok().body(colorService.getColorById(id));
    }

    @PostMapping
    public ResponseEntity<ColorResponseDTO> post(@RequestBody ColorRequestDTO colorRequest) {
        return ResponseEntity.ok().body(colorService.saveColor(colorRequest));
    }
}
