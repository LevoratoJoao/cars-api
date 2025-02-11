package com.car_dealership.cars_api.models;

public enum UserRole {
    ADMIN("admin"),
    SELLER("seller"),
    TEST("test");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
