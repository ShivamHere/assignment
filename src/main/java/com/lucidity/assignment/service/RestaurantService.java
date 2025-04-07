package com.lucidity.assignment.service;

import com.lucidity.assignment.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestaurantService {
    private Map<Long, Restaurant> restaurants = new HashMap<>();

    public Restaurant addRestaurant(Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public Restaurant getRestaurant(Long id) {
        return restaurants.get(id);
    }
}
