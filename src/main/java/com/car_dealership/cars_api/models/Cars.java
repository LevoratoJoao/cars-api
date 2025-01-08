package com.car_dealership.cars_api.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "cars")
@Entity // Creates a table of this clas
@Setter
@Getter
@ToString // @Data already creates Getter, Setter and ToString so we can use that too
@NoArgsConstructor
@AllArgsConstructor
public class Cars {
    @Id
    @GeneratedValue
    private Integer id;

    private String car_name;
    private String brand;
    private String model;
    private Integer release_year;
    private String color;
    private String motor;
    private Float kilometers;
    private Float price;
}
