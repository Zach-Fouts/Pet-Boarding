package com.petboarding.models.dto;

import com.petboarding.models.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Reservation extends AbstractEntity {
//    ******FUTURE REFERENCES TO BE ADDED AT A LATER DATE********
//    @ManyToMany
//    private Pet pet;
//
//    @ManyToOne
//    private Kennel kennel;
    //**************************


    @NotBlank
    private Date startDateTime;

    @NotBlank
    private Date endDateTime;

    @Size(max = 250, message = "A comment cannot be longer than 250 characters.")
    private String Comments;

    @NotBlank
    private String createdBy;

    @Column(columnDefinition = "boolean default false")
    private boolean active;

    public Reservation() {
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
