package com.petboarding.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
public class StayService extends AbstractDetailEntity{

    @Valid
    @ManyToOne
    @JoinColumn(name = "stay_id")
    private Stay stay;

   public StayService() {
   }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }
}
