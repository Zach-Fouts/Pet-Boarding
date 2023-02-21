package com.petboarding.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Kennel extends AbstractEntity{

    @NotNull
    @Size(max = 25, message = "The name cannot be longer than 50 characters.")
    private String name;

    public Kennel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
