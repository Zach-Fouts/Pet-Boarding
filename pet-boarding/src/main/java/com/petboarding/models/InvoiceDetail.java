package com.petboarding.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class InvoiceDetail extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private PetService service;

    @NotNull(message = "The quantity is required.")
    @Column(columnDefinition = "float(3, 2) default 1.0")
    private Float quantity;

    @NotNull(message = "The price per unit is required.")
    private Double pricePerUnit;

    public InvoiceDetail() {
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PetService getService() {
        return service;
    }

    public void setService(PetService service) {
        this.service = service;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
