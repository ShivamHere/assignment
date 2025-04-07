package com.lucidity.assignment.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Order {
    private Long orderId;
    private Long userId;
    private Long restaurantId;

    public Order() {}

    public Order(Long orderId, Long userId, Long restaurantId) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
