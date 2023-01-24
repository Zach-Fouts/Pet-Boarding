package com.petboarding.models;

import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Employee extends AbstractEntity{

    @NotBlank(message = "First name cannot be empty.")
    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;

    @Valid
    @OneToOne
    private Position position;

    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 100, message = "Address cannot be longer than 100 characters.")
    private String address;

    @Size(max = 100, message = "Address 2 cannot be longer than 100 characters.")
    private String address2;

    @NotBlank(message = "Phone number cannot be empty.")
    @Size(min = 10, message = "Phone number cannot be shorter than 10 characters.")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be empty.")
    @Email
    private String email;

    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    public Employee() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.lastName + ", " + this.firstName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
