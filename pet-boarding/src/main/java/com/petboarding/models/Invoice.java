package com.petboarding.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Invoice extends AbstractEntity{

    @Column(nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private Integer number;

    @OneToOne
    @JoinColumn(name = "stay_id")
    private Stay stay;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceDetail> details;

    public Invoice() {}

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Set<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<InvoiceDetail> details) {
        this.details = details;
    }
}
