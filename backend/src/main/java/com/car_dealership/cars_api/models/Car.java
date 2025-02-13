package com.car_dealership.cars_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "car")
@Entity // Creates a table of this clas
@Setter
@Getter
@ToString // @Data already creates Getter, Setter and ToString so we can use that too
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer car_id;

    private String car_name;
    private String model;
    private Integer release_year;
    private String motor;
    private Float kilometers;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "manufacturer")
    private Manufacturer manufacturer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "car_colors",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors = new HashSet<>();

    public Car(String car_name, String model, Integer release_year, String motor, Float kilometers, Float price, Manufacturer manufacturer, Set<Color> colors) {
        this.car_name = car_name;
        this.model = model;
        this.release_year = release_year;
        this.motor = motor;
        this.kilometers = kilometers;
        this.price = price;
        this.manufacturer = manufacturer;
        this.colors = colors;
    }
}
