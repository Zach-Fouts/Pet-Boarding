package com.petboarding.models;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Position extends AbstractEntity{

    @NotBlank
    @Size(max = 50, message = "Name cannot be longer than 100 characters.")
    private String name;

    @OneToMany
    @JoinColumn(name = "position_id")
    private List<Employee> employees = new ArrayList<>();


    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    public Position() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() { return employees; }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
