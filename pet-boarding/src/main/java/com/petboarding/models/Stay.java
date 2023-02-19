package com.petboarding.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Stay extends AbstractEntity{

    @Valid
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    //TODO: Add support for Kennel model
//    @Valid
//    @ManyToOne
//    private Kennel kennel;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StayStatus status;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public StayStatus getStatus() {
        return status;
    }

    public void setStatus(StayStatus status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
