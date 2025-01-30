package com.car_dealership.cars_api.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "customer")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customer_id;

    private String first_name;
    private String last_name;
    private String phone_number;

    @Column(unique = true, nullable = false)
    private String email;

    public Customer(String first_name, String last_name, String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
    }
}
