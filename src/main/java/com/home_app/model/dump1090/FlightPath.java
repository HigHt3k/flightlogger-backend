package com.home_app.model.dump1090;

import com.home_app.model.planespotting.Aircraft;
import jakarta.persistence.*;

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
}
