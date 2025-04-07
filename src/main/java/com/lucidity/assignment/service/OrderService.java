package com.lucidity.assignment.service;

import com.lucidity.assignment.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private List<Order> orders = new ArrayList<>();

    public Order addOrder(Order order) {
        orders.add(order);
        return order;
    }

    public List<Order> addBatchOrders(List<Order> ordersBatch) {
        orders.addAll(ordersBatch);
        return ordersBatch;
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}
