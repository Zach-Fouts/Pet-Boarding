package com.petboarding.models;


import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity{

    private String username;

    public User() {}
    public User(String username, String password) {
        this.username = username;
    }

}
