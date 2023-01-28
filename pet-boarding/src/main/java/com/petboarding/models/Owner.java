package com.petboarding.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Just a name for now
@Entity
public class Owner extends AbstractEntity{

    private int id;

    private static int nextId = 1;

    private String firstName;
    private String lastName;

    private String address;
    private String address2;
    private String phoneNumber;
    private String email;
    private String notes;


    public Owner(){
        id = nextId;
        nextId++;
    }

    public Owner(String aFirstName, String aLastName, String aAddress, String aAdress2, String aPhoneNumber, String aEmail, String aNotes){

        firstName = aFirstName;
        lastName = aLastName;
        address = aAddress;
        address2 = aAdress2;
        phoneNumber = aPhoneNumber;
        email = aEmail;
        notes = aNotes;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
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

    public int getId() {
        return id;
    }
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
