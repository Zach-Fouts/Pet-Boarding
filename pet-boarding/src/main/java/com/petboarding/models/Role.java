package com.petboarding.models;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role extends AbstractEntity{
    @OneToMany
    private List<User> users = new ArrayList<>();

    private String name;


    public Role() {}

    public Role(String name){
        super();
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
