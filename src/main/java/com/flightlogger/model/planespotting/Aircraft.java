package com.flightlogger.model.planespotting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flightlogger.model.dump1090.FlightLog;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name =  "aircraft")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Aircraft {
    @Id
    @Column(name = "icao_24")
    private Integer icao24;
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
    @Column(name = "icao_24")
    private List<Sighting> sightings;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    @Column(name = "icao_24")
    private List<FlightLog> flightLogs;

    public Integer getIcao24() {
        return icao24;
    }

    public void setIcao24(Integer icao24) {
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

    public List<FlightLog> getFlightLogs() {
        return flightLogs;
    }

    public void setFlightLogs(List<FlightLog> flightLogs) {
        this.flightLogs = flightLogs;
    }
}
