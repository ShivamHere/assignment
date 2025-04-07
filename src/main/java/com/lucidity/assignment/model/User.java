package com.lucidity.assignment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private Location location;
    private String contact;

    public User() {}

    public User(Long id, String name, Location location, String contact) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contact = contact;
    }
}
