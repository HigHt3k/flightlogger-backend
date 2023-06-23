package com.home_app.model.planespotting;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name =  "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id")
    private Integer aircraftId;
    @Column(name = "icao_24")
    private String icao24;
    @Column(name = "aircraft_registration")
    private String aircraftRegistration;
    @Column(name = "aircraft_manufacturer")
    private String aircraftManufacturer;
    @Column(name = "aircraft_type")
    private String aircraftType;
    @Column(name = "operator")
    private String operator;
    @Column(name = "operator_code")
    private String operatorCode;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    @Column(name = "aircraft_id")
    private List<Sighting> sightings;

    public Integer getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Integer aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public void setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
    }

    public String getAircraftManufacturer() {
        return aircraftManufacturer;
    }

    public void setAircraftManufacturer(String aircraftManufacturer) {
        this.aircraftManufacturer = aircraftManufacturer;
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

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }
}
