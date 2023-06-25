package com.home_app.model.dump1090;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightPathRepository extends JpaRepository<FlightPath, Integer> {
    List<FlightPath> findAll();
    List<FlightPath> findByFlightLog(FlightLog flightLog);

}
