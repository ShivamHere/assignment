package com.lucidity.assignment.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeliveryAgent {
    private Long id;
    private String name;
    private Location currentLocation;
    private String contact;

    public DeliveryAgent() {}

    public DeliveryAgent(Long id, String name, Location currentLocation, String contact) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.contact = contact;
    }
}
