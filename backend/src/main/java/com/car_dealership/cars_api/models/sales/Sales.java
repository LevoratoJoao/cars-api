package com.car_dealership.cars_api.models.sales;

import com.car_dealership.cars_api.models.car.Car;
import com.car_dealership.cars_api.models.customer.Customer;
import com.car_dealership.cars_api.models.employee.Employee;
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

    public Sales(LocalDate sale_date, Float sale_price, Customer customer, Car car, Employee employee) {}
}
