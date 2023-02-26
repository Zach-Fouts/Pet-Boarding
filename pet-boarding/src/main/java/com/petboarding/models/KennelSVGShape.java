package com.petboarding.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;


// Model to hold kennel Map SVG shapes
@Entity
public class KennelSVGShape extends AbstractEntity{

    @OneToOne
    @JoinColumn(name = "kennel_id")
    private Kennel kennel;

    @Column(name="x_Pos", precision = 7, scale = 3)
    private float xPos;
    @Column(name="y_Pos", precision = 7, scale = 3)
    private float yPos;
    @Column(precision = 7, scale = 3)
    private float width;
    @Column(precision = 7, scale = 3)
    private float height;


    public Kennel getKennel() {
        return kennel;
    }

    public void setKennel(Kennel kennel) {
        this.kennel = kennel;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }



}
