package com.car_dealership.cars_api.models.manufacturer;

import com.car_dealership.cars_api.models.car.Car;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "manufacturer")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer man_id;

    private String man_name;
    private String country;

    @OneToMany(mappedBy = "manufacturer")
    private List<Car> cars;

    public Manufacturer(String man_name, String country) {
        this.man_name = man_name;
        this.country = country;
    }
}
