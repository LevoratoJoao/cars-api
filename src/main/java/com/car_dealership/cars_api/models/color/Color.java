package com.car_dealership.cars_api.models.color;

import com.car_dealership.cars_api.models.car.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "color")
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer color_id;

    private String color_name;

    public Color(String color_name) {
        this.color_name = color_name;
    }

    @ManyToMany(mappedBy = "colors")
    @JsonIgnore
    private List<Car> cars;
}
