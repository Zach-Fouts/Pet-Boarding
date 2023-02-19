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
}
