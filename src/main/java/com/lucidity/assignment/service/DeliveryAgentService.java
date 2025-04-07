package com.lucidity.assignment.service;

import com.lucidity.assignment.model.DeliveryAgent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeliveryAgentService {
    private Map<Long, DeliveryAgent> agents = new HashMap<>();

    public DeliveryAgent addDeliveryAgent(DeliveryAgent agent) {
        agents.put(agent.getId(), agent);
        return agent;
    }

    public DeliveryAgent getDeliveryAgent(Long id) {
        return agents.get(id);
    }

    public Map<Long, DeliveryAgent> getAllAgents() {
        return agents;
    }
}
