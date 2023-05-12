package com.home_app.model.plant;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "houseplants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String species;

    private Integer wateringCycle;

    private String sunlightNeeds;

    private Timestamp lastWatered;

    private Timestamp lastFertilized;

    private Timestamp nextWatering;

    private Timestamp nextFertilizing;

    private String location;

    private String direction;

    private Integer preferredTemperature;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getWateringCycle() {
        return wateringCycle;
    }

    public void setWateringCycle(Integer wateringCycle) {
        this.wateringCycle = wateringCycle;
    }

    public String getSunlightNeeds() {
        return sunlightNeeds;
    }

    public void setSunlightNeeds(String sunlightNeeds) {
        this.sunlightNeeds = sunlightNeeds;
    }

    public Timestamp getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(Timestamp lastWatered) {
        this.lastWatered = lastWatered;
    }

    public Timestamp getLastFertilized() {
        return lastFertilized;
    }

    public void setLastFertilized(Timestamp lastFertilized) {
        this.lastFertilized = lastFertilized;
    }

    public Timestamp getNextWatering() {
        return nextWatering;
    }

    public void setNextWatering(Timestamp nextWatering) {
        this.nextWatering = nextWatering;
    }

    public Timestamp getNextFertilizing() {
        return nextFertilizing;
    }

    public void setNextFertilizing(Timestamp nextFertilizing) {
        this.nextFertilizing = nextFertilizing;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getPreferredTemperature() {
        return preferredTemperature;
    }

    public void setPreferredTemperature(Integer preferredTemperature) {
        this.preferredTemperature = preferredTemperature;
    }
}