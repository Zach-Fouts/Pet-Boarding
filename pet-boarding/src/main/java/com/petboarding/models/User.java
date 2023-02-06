package com.petboarding.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEntity{
//<---------------------------------------------Fields--------------------------------------------->
    @NotNull
    @NotBlank
    @Size(min = 3, max = 20, message = "Invalid username. Must be between 3 and 20 characters")
    private String username;

    @NotNull
    private String pwHash;

    @Valid
    @ManyToOne
    private Role role;

    @Valid
    @OneToOne
    private Employee employee;


    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    //<---------------------------------------------Methods--------------------------------------------->
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Boolean isAdmin() {
        return role.getName().toLowerCase().equals("admin");
    }

    //<---------------------------------------------Constructors--------------------------------------------->
    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }
    public User(String username, String password, Role role, Employee employee) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.role = role;
        this.employee = employee;
    }

    //<---------------------------------------------Getters and Setters--------------------------------------------->
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}
