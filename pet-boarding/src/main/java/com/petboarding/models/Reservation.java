package com.petboarding.models;

import com.petboarding.models.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Reservation extends AbstractEntity {

    @ManyToOne
    private Pet pet;
//    ******FUTURE REFERENCES TO BE ADDED AT A LATER DATE********
//    @ManyToOne
//    private Kennel kennel;
    //**************************

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date is required")
    private Date startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date is required")
    private Date endDateTime;

    @NotNull
    @Size(max = 250, message = "A comment cannot be longer than 250 characters.")
    private String comments;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Reservation() {
    }
    public Reservation(Date aStartDateTime, Date anEndDateTime, String aComment) {
        super();
        this.startDateTime = aStartDateTime;
        this.endDateTime = anEndDateTime;
        this.comments = aComment;
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
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
