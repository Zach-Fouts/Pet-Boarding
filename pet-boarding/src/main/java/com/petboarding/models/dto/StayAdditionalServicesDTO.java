package com.petboarding.models.dto;


import com.petboarding.models.StayService;

import java.util.List;

public class StayAdditionalServicesDTO {

    private List<StayService> services;

    public List<StayService> getServices() {
        return services;
    }

    public void setServices(List<StayService> services) {
        this.services = services;
    }
}
