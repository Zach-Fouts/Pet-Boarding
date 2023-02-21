package com.petboarding.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Kennel extends AbstractEntity{

    @OneToMany
    @JoinColumn(name = "id")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "id")
    private List<Stay> stays = new ArrayList<>();

    private String name;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Stay> getStays() {
        return stays;
    }

    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }

    public String getName() {
        return name;
    }
}
