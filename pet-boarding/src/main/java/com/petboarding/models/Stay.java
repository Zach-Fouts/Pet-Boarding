package com.petboarding.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Stay extends AbstractEntity{

    @Valid
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Valid
    @ManyToOne
    private Kennel kennel;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StayStatus status;

    @OneToMany(mappedBy = "stay")
    private Set<StayService> additionalServices;

    @OneToOne(mappedBy = "stay")
    private Invoice invoice;

    public Stay() {
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Kennel getKennel() {
        return kennel;
    }

    public void setKennel(Kennel kennel) {
        this.kennel = kennel;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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

    public Set<StayService> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<StayService> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
