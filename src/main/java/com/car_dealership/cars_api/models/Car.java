package com.car_dealership.cars_api.models;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "car")
@Entity // Creates a table of this clas
@Setter
@Getter
@ToString // @Data already creates Getter, Setter and ToString so we can use that too
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String car_name;
    private String brand;
    private String model;
    private Integer release_year;
    private String color;
    private String motor;
    private Float kilometers;
    private Float price;

    public Car(String car_name, String brand, String model, Integer release_year, String color, String motor, Float kilometers, Float price) {
        this.car_name = car_name;
        this.brand = brand;
        this.model = model;
        this.release_year = release_year;
        this.color = color;
        this.motor = motor;
        this.kilometers = kilometers;
        this.price = price;
    }
}
