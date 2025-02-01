package com.car_dealership.cars_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "sales")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer sales_id;

    @NonNull
    private LocalDate sale_date;

    @NonNull
    private Float sale_price;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Sales(LocalDate sale_date, Float sale_price, Customer customer, Car car, Employee employee) {
        this.sale_date = sale_date;
        this.sale_price = sale_price;
        this.customer = customer;
        this.car = car;
        this.employee = employee;
    }
}
