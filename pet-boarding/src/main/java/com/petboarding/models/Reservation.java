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

    private String confirmation;

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

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public void assignConfirmationCode(){
        confirmation = generateConfirmationCode();
    }
    private String generateConfirmationCode() {
        // get code based on current milliseconds
        String code = getCodeFromLong(System.currentTimeMillis());
        try {
            while(true) {
                // test if the code is decimal number
                long longCode = Long.parseLong(code);
                // turn new number into Hexadecimal
                code = getCodeFromLong(longCode);
            }
        } catch(Exception e) {}
        return code;
    }

    private String getCodeFromLong(long number) {
        if(number < 4096) number += 4096; //force 4 digit hexadecimal
        String code = Long.toHexString(number);
        return code.substring( code.length() - 4 ).toUpperCase();
    }

}
