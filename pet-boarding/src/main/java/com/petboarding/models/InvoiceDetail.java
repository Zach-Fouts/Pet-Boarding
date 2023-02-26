package com.petboarding.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class InvoiceDetail extends AbstractDetailEntity{

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public InvoiceDetail() {
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
