package com.home_app.model.planespotting;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name =  "sightings")
public class Sighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sightingId;
    private Timestamp sightingDate;
    private String aircraftRegistration;
    private String aircraftType;
    private String operator;
    private String flightNumber;
    @Column(name = "arrival_icao")
    private String arrivalICAO;
    @Column(name = "arrival_iata")
    private String arrivalIATA;
    @Column(name = "departure_icao")
    private String departureICAO;
    @Column(name = "departure_iata")
    private String departureIATA;
    @Column(name = "spotted_at_icao")
    private String spottedAtICAO;
    @Column(name = "spotted_at_iata")
    private String spottedAtIATA;

    @OneToMany(mappedBy = "sighting", cascade = CascadeType.ALL)
    private List<SightingImage> sightingImages;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icao_24")
    private Aircraft aircraft;

    public Integer getSightingId() {
        return sightingId;
    }

    public void setSightingId(Integer sightingId) {
        this.sightingId = sightingId;
    }

    public Timestamp getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(Timestamp sightingDate) {
        this.sightingDate = sightingDate;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public void setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getArrivalICAO() {
        return arrivalICAO;
    }

    public void setArrivalICAO(String arrivalICAO) {
        this.arrivalICAO = arrivalICAO;
    }

    public String getArrivalIATA() {
        return arrivalIATA;
    }

    public void setArrivalIATA(String arrivalIATA) {
        this.arrivalIATA = arrivalIATA;
    }

    public String getDepartureICAO() {
        return departureICAO;
    }

    public void setDepartureICAO(String departureICAO) {
        this.departureICAO = departureICAO;
    }

    public String getDepartureIATA() {
        return departureIATA;
    }

    public void setDepartureIATA(String departureIATA) {
        this.departureIATA = departureIATA;
    }

    public String getSpottedAtICAO() {
        return spottedAtICAO;
    }

    public void setSpottedAtICAO(String spottedAtICAO) {
        this.spottedAtICAO = spottedAtICAO;
    }

    public String getSpottedAtIATA() {
        return spottedAtIATA;
    }

    public void setSpottedAtIATA(String spottedAtIATA) {
        this.spottedAtIATA = spottedAtIATA;
    }

    public List<SightingImage> getSightingImages() {
        return sightingImages;
    }

    public void setSightingImages(List<SightingImage> sightingImages) {
        this.sightingImages = sightingImages;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}
