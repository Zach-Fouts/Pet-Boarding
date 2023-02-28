package com.petboarding.models.utils;

import com.petboarding.models.InvoiceDetail;

import java.util.List;
import java.util.Set;

public class InvoiceAggregatedInformation {

    private String servicesList;
    private double subTotal;

    public InvoiceAggregatedInformation(){}

    public InvoiceAggregatedInformation(List<InvoiceDetail> details) {
        processDetails(details);
    }

    public void processDetails(List<InvoiceDetail> details) {
        servicesList = "<ul>";
        subTotal = 0.0;
        for(InvoiceDetail detail : details) {
            subTotal += detail.getSubTotal();
            servicesList += "<li>" + (detail.getService() != null ? detail.getService().getName() : "null") + "</li>";
        }
        servicesList += "</ul>";
    }

    public String getServicesList() {
        return servicesList;
    }

    public double getSubTotal() {
        return subTotal;
    }

}
