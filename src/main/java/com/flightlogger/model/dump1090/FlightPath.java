package com.flightlogger.model.dump1090;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "flight_path")
public class FlightPath {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "flight_path_id")
    private Integer flightPathId;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "altitude")
    private int altitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_log_id")
    private FlightLog flightLog;

    @Column(name = "create_ts")
    private Timestamp createTs;

    public Integer getFlightPathId() {
        return flightPathId;
    }

    public void setFlightPathId(Integer flightPathId) {
        this.flightPathId = flightPathId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public FlightLog getFlightLog() {
        return flightLog;
    }

    public void setFlightLog(FlightLog flightLog) {
        this.flightLog = flightLog;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }
}
