package com.home_app.model.dump1090;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightPathRepository extends JpaRepository<FlightPath, Integer> {
    List<FlightPath> findAll();
    List<FlightPath> findByFlightLog(FlightLog flightLog);

    @Query("SELECT max(f.createTs) FROM FlightPath f WHERE f.flightLog.flightLogId = :flight_log_id")
    Optional<Timestamp> findNewestFlightPathEntry(@Param("flight_log_id") Integer flightLogId);
}
