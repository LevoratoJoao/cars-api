package com.car_dealership.cars_api.models.employee;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "employee")
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_id;

    private String first_name;
    private String last_name;

    private String position;
    private Float salary;
    private LocalDate hire_date;
    private String phone_number;

    @Column(unique = true, nullable = false)
    private String email;

    public Employee(String first_name, String last_name, String position, Float salary, LocalDate hire_date, String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.position = position;
        this.salary = salary;
        this.hire_date = hire_date;
        this.phone_number = phone_number;
        this.email = email;
    }
}
