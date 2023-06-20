package com.home_app.model.planespotting;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

public class Sighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sightingId;
    private Timestamp sightingDate;
    private String aircraftRegistration;
    private String aircraftType;
    private String operator;
    private String flightNumber;
    private String arrivalICAO;
    private String arrivalIATA;
    private String departureICAO;
    private String departureIATA;

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
}
