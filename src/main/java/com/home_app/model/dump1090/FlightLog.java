package com.home_app.model.dump1090;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.home_app.model.planespotting.Aircraft;
import com.home_app.model.planespotting.Sighting;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "flight_log")
public class FlightLog {
    @Id
    @Column(name = "flight_log_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int flightLogId;
    private double firstLatitude;
    private double firstLongitude;
    private double lastLatitude;
    private double lastLongitude;
    private int firstAltitude;
    private int lastAltitude;
    private boolean emergencyFlag;
    private Timestamp firstTs;
    private Timestamp lastTs;
    private String callsign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icao_24")
    @JsonIgnore
    private Aircraft aircraft;

    @OneToMany(mappedBy = "flightLog", cascade = CascadeType.ALL)
    @Column(name = "flight_log_id")
    @JsonIgnore
    private List<FlightPath> flightPaths;

    public int getFlightLogId() {
        return flightLogId;
    }

    public void setFlightLogId(int flightLogId) {
        this.flightLogId = flightLogId;
    }

    public double getFirstLatitude() {
        return firstLatitude;
    }

    public void setFirstLatitude(double firstLatitude) {
        this.firstLatitude = firstLatitude;
    }

    public double getFirstLongitude() {
        return firstLongitude;
    }

    public void setFirstLongitude(double firstLongitude) {
        this.firstLongitude = firstLongitude;
    }

    public double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public int getFirstAltitude() {
        return firstAltitude;
    }

    public void setFirstAltitude(int firstAltitude) {
        this.firstAltitude = firstAltitude;
    }

    public int getLastAltitude() {
        return lastAltitude;
    }

    public void setLastAltitude(int lastAltitude) {
        this.lastAltitude = lastAltitude;
    }

    public boolean isEmergencyFlag() {
        return emergencyFlag;
    }

    public void setEmergencyFlag(boolean emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }

    public Timestamp getFirstTs() {
        return firstTs;
    }

    public void setFirstTs(Timestamp firstTs) {
        this.firstTs = firstTs;
    }

    public Timestamp getLastTs() {
        return lastTs;
    }

    public void setLastTs(Timestamp lastTs) {
        this.lastTs = lastTs;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
}
