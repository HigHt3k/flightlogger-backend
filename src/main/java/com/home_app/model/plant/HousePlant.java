package com.home_app.model.plant;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
public class HousePlant {
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

    public HousePlant(Integer id, String name, String species, Integer wateringCycle, String sunlightNeeds, Timestamp lastWatered, Timestamp lastFertilized, Timestamp nextWatering, Timestamp nextFertilizing, String location, String direction, Integer preferredTemperature) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.wateringCycle = wateringCycle;
        this.sunlightNeeds = sunlightNeeds;
        this.lastWatered = lastWatered;
        this.lastFertilized = lastFertilized;
        this.nextWatering = nextWatering;
        this.nextFertilizing = nextFertilizing;
        this.location = location;
        this.direction = direction;
        this.preferredTemperature = preferredTemperature;
    }

    public HousePlant() {

    }

    public HousePlant(Integer id, String name, String species, Integer wateringCycle, String sunlightNeeds, String location, String direction, Integer preferredTemperature) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.wateringCycle = wateringCycle;
        this.sunlightNeeds = sunlightNeeds;
        this.location = location;
        this.direction = direction;
        this.preferredTemperature = preferredTemperature;
    }

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