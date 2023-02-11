package com.petboarding.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
public class Stay extends AbstractEntity{

    @Valid
    @OneToOne
    private Reservation reservation;

    //TODO: Add support for Kennel model
//    @Valid
//    @ManyToOne
//    private Kennel kennel;

    @Valid
    @ManyToOne
    private Employee caretaker;

    @Column(columnDefinition = "boolean default true")
    private Boolean active = true;

    public Stay() {
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Employee getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Employee caretaker) {
        this.caretaker = caretaker;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
