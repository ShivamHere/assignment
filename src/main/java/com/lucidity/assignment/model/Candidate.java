package com.lucidity.assignment.model;

import lombok.Getter;

@Getter
public class Candidate {
    private String label; // e.g., "restaurant_123" or "user_123"
    private double cost;  // travel + waiting time from current state
    private Location location; // destination location
    private Order order;  // associated order
    private boolean isRestaurant; // true if candidate is a restaurant

    public Candidate(String label, double cost, Location location, Order order, boolean isRestaurant) {
        this.label = label;
        this.cost = cost;
        this.location = location;
        this.order = order;
        this.isRestaurant = isRestaurant;
    }
}
