package com.lucidity.assignment.controller;

import com.lucidity.assignment.model.DeliveryAgent;
import com.lucidity.assignment.model.Order;
import com.lucidity.assignment.service.DeliveryAgentService;
import com.lucidity.assignment.service.DeliveryOptimizationService;
import com.lucidity.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryAgentService deliveryAgentService;

    @Autowired
    private DeliveryOptimizationService optimizationService;

    @PostMapping("/addDeliveryAgent")
    public DeliveryAgent addDeliveryAgent(@RequestBody DeliveryAgent agent) {
        return deliveryAgentService.addDeliveryAgent(agent);
    }

    @PostMapping("/getDeliveryAgent")
    public DeliveryAgent getDeliveryAgent(@RequestParam Long id) {
        // Returns the best delivery agent using a greedy approach
        return deliveryAgentService.getDeliveryAgent(id);
    }

    //This could be made trigger based, as async call after order is made
    @PostMapping("/getBestDeliveryAgent")
    public DeliveryAgent getBestDeliveryAgent(@RequestBody List<Order> orders) {
        // Returns the best delivery agent using a greedy approach
        return optimizationService.getBestDeliveryAgent(orders);
    }

    //This could be made trigger based, as async call after we have order and best deliveryAgent
    @PostMapping("/getBestPath")
    public Map<String, Object> getBestPath(@RequestParam Long deliveryAgentId,
                                           @RequestBody List<Order> orders) {
        return optimizationService.getBestPath(deliveryAgentId, orders);
    }
}
