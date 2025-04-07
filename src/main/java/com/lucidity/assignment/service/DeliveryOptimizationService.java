package com.lucidity.assignment.service;

import com.lucidity.assignment.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeliveryOptimizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DeliveryAgentService deliveryAgentService;

    // Utility: Haversine formula to calculate travel time (hours)
    private double calculateTravelTime(Location a, Location b) {
        final double AVERAGE_SPEED_KMH = 20.0;
        final int EARTH_RADIUS = 6371;
        double latDiff = Math.toRadians(b.getLatitude() - a.getLatitude());
        double lonDiff = Math.toRadians(b.getLongitude() - a.getLongitude());
        double lat1 = Math.toRadians(a.getLatitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double haversine = Math.pow(Math.sin(latDiff / 2), 2)
                + Math.pow(Math.sin(lonDiff / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));
        double distance = EARTH_RADIUS * c;
        return distance / AVERAGE_SPEED_KMH;
    }

    // Greedy approach to select the closest delivery agent for a batch of orders.
    // It selects the agent that minimizes the travel time to the restaurant with the least waiting time.
    public DeliveryAgent getBestDeliveryAgent(List<Order> orders) {
        // Determine the restaurant with the least avgPreparationTime among orders.
        Restaurant bestRestaurant = null;
        for (Order order : orders) {
            Restaurant restaurant = restaurantService.getRestaurant(order.getRestaurantId());
            if (restaurant != null) {
                if (bestRestaurant == null ||
                        restaurant.getAvgPreparationTime() < bestRestaurant.getAvgPreparationTime()) {
                    bestRestaurant = restaurant;
                }
            }
        }
        if (bestRestaurant == null) {
            return null;
        }
        // Find the delivery agent closest to the chosen restaurant.
        DeliveryAgent bestAgent = null;
        double minTime = Double.MAX_VALUE;
        for (DeliveryAgent agent : deliveryAgentService.getAllAgents().values()) {
            double travelTime = calculateTravelTime(agent.getCurrentLocation(), bestRestaurant.getLocation());
            if (travelTime < minTime) {
                minTime = travelTime;
                bestAgent = agent;
            }
        }
        return bestAgent;
    }

    // Calculate the best path for a delivery agent given a list of orders.
    // This uses a graph-based approach with a greedy to nearest-first.
    public Map<String, Object> getBestPath(Long deliveryAgentId, List<Order> orders) {
        // Retrieve the delivery agent.
        DeliveryAgent agent = deliveryAgentService.getDeliveryAgent(deliveryAgentId);
        if (agent == null) {
            throw new RuntimeException("Delivery agent not found");
        }

        double currentTime = 0.0; // in hours
        Location currentLocation = agent.getCurrentLocation();
        List<String> route = new ArrayList<>();
        route.add("agent"); // starting point

        // Separate orders into not-picked-up and picked-up lists.
        List<Order> notPicked = new ArrayList<>(orders);
        List<Order> pickedUp = new ArrayList<>();

        // Continue until all orders are delivered (i.e. no orders remain in either list)
        while (!notPicked.isEmpty() || !pickedUp.isEmpty()) {
            // Use a PriorityQueue to pick the candidate with the smallest effective cost.
            PriorityQueue<Candidate> pq = new PriorityQueue<>(Comparator.comparingDouble(Candidate::getCost));

            // Add candidates for restaurants (orders not yet picked up)
            for (Order order : notPicked) {
                Restaurant restaurant = restaurantService.getRestaurant(order.getRestaurantId());
                if (restaurant == null) continue;
                double travelTime = calculateTravelTime(currentLocation, restaurant.getLocation());
                // Arrival time = currentTime + travelTime.
                // If arrival is before prep time, add extra wait time.
                double waitTime = Math.max(0, restaurant.getAvgPreparationTime() - (currentTime + travelTime));
                double totalCost = travelTime + waitTime;
                pq.add(new Candidate("restaurant_" + order.getOrderId(), totalCost, restaurant.getLocation(), order, true));
            }

            // Add candidates for users (orders already picked up but not delivered)
            for (Order order : pickedUp) {
                User user = userService.getUser(order.getUserId());
                if (user == null) continue;
                double travelTime = calculateTravelTime(currentLocation, user.getLocation());
                pq.add(new Candidate("user_" + order.getOrderId(), travelTime, user.getLocation(), order, false));
            }

            // If no candidate exists, break (shouldn't occur normally).
            if (pq.isEmpty()) {
                break;
            }

            Candidate best = pq.poll();
            // Update route and current state.
            route.add(best.getLabel());
            currentTime += best.getCost();
            currentLocation = best.getLocation();

            // If the best candidate is a restaurant, we "pick up" the order.
            if (best.isRestaurant()) {
                Order order = best.getOrder();
                notPicked.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
                pickedUp.add(order);
            } else {
                // Otherwise, the candidate is a user – mark order as delivered.
                Order order = best.getOrder();
                pickedUp.removeIf(o -> o.getOrderId().equals(order.getOrderId()));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("route", route);
        result.put("totalTime", currentTime);
        return result;
    }

}
