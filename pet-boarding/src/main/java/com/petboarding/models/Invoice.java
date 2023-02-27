package com.petboarding.models;

import com.petboarding.controllers.utils.DateUtils;
import com.petboarding.models.data.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;

@Entity
public class Invoice extends AbstractEntity{
    @Column(nullable = false, updatable = false)
    private Date date;

    @Column(nullable = false, updatable = false)
    private Integer number;

    @OneToOne
    @JoinColumn(name = "stay_id", updatable = false)
    private Stay stay;

    @ManyToOne
    @JoinColumn(name = "owner_id", updatable = false)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceDetail> details = new HashSet<>();

    private float discountPercent;
    private float taxPercent;

    public Invoice() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFullNumber() {
        if(date == null) return "";
        return DateUtils.format(this.date, "yyyy") + "." + this.number;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
        this.owner = stay.getReservation().getPet().getOwner();
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

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }


    public float getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(float taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getServicesList() {
        return "Services_list";
    }

    public Double subTotal() {
        double subTotal = 0.0;
        for(InvoiceDetail detail: details) {

        }
        return subTotal;
    }

    public Double getTotal() {
        return 100.00;
    }

}

