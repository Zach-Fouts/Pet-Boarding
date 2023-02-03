package com.petboarding.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Just a name for now
@Entity
public class Owner extends AbstractEntity{

    @NotBlank(message = "First name cannot be empty.")
    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty.")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;
    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 100, message = "Address cannot be longer than 100 characters.")
    private String address;
    @Size(max = 100, message = "Address 2 cannot be longer than 100 characters.")
    private String address2;
    @Size(min = 10, message = "Phone number cannot be shorter than 10 characters.")
    private String phoneNumber;
    @NotBlank(message = "Email cannot be empty.")
    @Email
    private String email;
    private String notes;


    public Owner(){
    }

    public Owner(String aFirstName, String aLastName, String aAddress, String aAddress2, String aPhoneNumber, String aEmail, String aNotes){

        firstName = aFirstName;
        lastName = aLastName;
        address = aAddress;
        address2 = aAddress2;
        phoneNumber = aPhoneNumber;
        email = aEmail;
        notes = aNotes;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + this.getId() +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", address=" + address +
                ", address2=" + address2 +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", notes=" + notes +
                '\'' +
                '}';
    }

    //Needed with Abstract Entitys?
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Owner owner = (Owner) o;
//        return id == owner.id && name.equals(owner.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name);
//    }

//    public long getId() {
//        return id;
//    }
    public String getName() {
        return "firstName" + " " + "lastName";
    }

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
