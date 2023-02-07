package com.petboarding.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Enter pet name!")
    @Column(name = "pet_name")
    private String petName;


//    @NotBlank(message = "Enter parent name!")     // Some Not Blank error?
    @ManyToOne                                      // Sets up relationship
    private Owner owner;                            // Parents -> Owner

    @NotBlank(message = "Enter breed type!")
    @Column(name = "breed")
    private String breed;
    @Column(name = "notes")
    private String notes;

    public Pet(String petName, Owner owner, String breed, String notes) {
        super();
        this.petName = petName;
        this.owner = owner;                         // Parents -> Owner
        this.breed = breed;
        this.notes = notes;
    }

    public Pet(){}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Owner getOwner() {
        return this.owner;
    }                   // Parents -> Owner

    public void setOwner(Owner owner) {                             // Parents -> Owner
        this.owner = owner;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
