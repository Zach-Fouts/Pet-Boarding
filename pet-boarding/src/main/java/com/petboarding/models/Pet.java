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


    @NotBlank(message = "Enter parent name!")
    @Column(name = "parents")
    private String parents;

    @NotBlank(message = "Enter breed type!")
    @Column(name = "breed")
    private String breed;
    @Column(name = "notes")
    private String notes;

    public Pet(String petName, String parents, String breed, String notes) {
        super();
        this.petName = petName;
        this.parents = parents;
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

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
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