package com.lucidity.assignment.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Restaurant {
    private Long id;
    private String name;
    private Location location;
    private String contact;
    private double avgPreparationTime; // in hours

    public Restaurant() {}

    public Restaurant(Long id, String name, Location location, String contact, double avgPreparationTime) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.avgPreparationTime = avgPreparationTime;
    }
}
