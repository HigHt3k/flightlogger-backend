package com.home_app.model.planespotting;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.awt.*;

public class SightingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sightingImageId;
    private String comment;
    private String imagePath;

    public Integer getSightingImageId() {
        return sightingImageId;
    }

    public void setSightingImageId(Integer sightingImageId) {
        this.sightingImageId = sightingImageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
