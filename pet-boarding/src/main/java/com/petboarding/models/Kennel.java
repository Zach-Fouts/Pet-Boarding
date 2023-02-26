package com.petboarding.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Kennel extends AbstractEntity{

    @NotNull
    @Size(max = 25, message = "The name cannot be longer than 50 characters.")
    private String name;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Stay> stays = new ArrayList<>();

    @OneToOne(mappedBy ="kennel")
    private KennelSVGShape kennelSVGShape;


    public Kennel() {
    }

    public KennelSVGShape getKennelSVGShape() {
        return kennelSVGShape;
    }

    public void setKennelSVGShape(KennelSVGShape kennelSVGShape) {
        this.kennelSVGShape = kennelSVGShape;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
