package com.lucidity.assignment.service;

import com.lucidity.assignment.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private Map<Long, User> users = new HashMap<>();

    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(Long id) {
        return users.get(id);
    }
}
