package com.example.documents;

import lombok.Data;

@Data
public class Vehicle {
    String registrationNumber;
    String color;  // Ensure color is stored in lowercase

    public Vehicle(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color.toLowerCase();
    }

    public String getColor() {
        return color.toLowerCase();
    }

    public void setColor(String color) {
        this.color = color.toLowerCase();
    }
}
