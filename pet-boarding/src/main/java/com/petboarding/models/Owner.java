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

    public Owner(){
        id = nextId;
        nextId++;
    }

    public Owner(String aFirstName, String aLastName){

        firstName = aFirstName;
        lastName = aLastName;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
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

    public int getId() {
        return id;
    }
}
