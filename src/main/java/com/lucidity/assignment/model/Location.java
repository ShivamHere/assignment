package com.lucidity.assignment.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Location {
    // Getters and Setters
    private double latitude;
    private double longitude;

    public Location() {}

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
