package com.car_dealership.cars_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "manufacturer")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manufacturer_id;

    private String manufacturer_name;
    private String country;

    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    private List<Car> cars;

    public Manufacturer(String man_name, String country) {
        this.manufacturer_name = man_name;
        this.country = country;
    }
}
