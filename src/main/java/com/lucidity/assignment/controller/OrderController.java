package com.lucidity.assignment.controller;

import com.lucidity.assignment.model.Order;
import com.lucidity.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public Order addOrder(@RequestBody Order order) {
        Order added = orderService.addOrder(order);
        return added;
    }

    @PostMapping("/addBatchOrders")
    public List<Order> addBatchOrders(@RequestBody List<Order> orders) {
        List<Order> addedOrders = orderService.addBatchOrders(orders);
        return addedOrders;
    }
}
