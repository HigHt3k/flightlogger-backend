package com.home_app.model.planespotting;

import jakarta.persistence.*;

import java.awt.*;

@Entity
@Table(name = "sighting_images")
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
