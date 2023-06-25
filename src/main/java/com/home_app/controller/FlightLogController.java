package com.home_app.controller;

import com.home_app.model.dump1090.FlightLog;
import com.home_app.model.dump1090.FlightLogRepository;
import com.home_app.model.planespotting.Aircraft;
import com.home_app.model.planespotting.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class FlightLogController {

    private AircraftRepository aircraftRepository;
    private FlightLogRepository flightLogRepository;

    @Autowired
    public FlightLogController(AircraftRepository aircraftRepository, FlightLogRepository flightLogRepository) {
        this.aircraftRepository = aircraftRepository;
        this.flightLogRepository = flightLogRepository;
    }

    @GetMapping("/api/aircraft/{flightLogId}")
    public ResponseEntity<Aircraft> getAircraftByFlightLodId(@PathVariable Integer flightLogId) {
        Optional<FlightLog> flightLog = flightLogRepository.findById(flightLogId);

        return flightLog.map(log -> ResponseEntity.ok(log.getAircraft())).orElse(null);
    }
}
