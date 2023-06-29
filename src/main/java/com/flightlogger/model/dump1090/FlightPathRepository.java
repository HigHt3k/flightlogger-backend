package com.flightlogger.model.dump1090;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightPathRepository extends JpaRepository<FlightPath, Integer> {
    List<FlightPath> findAll();
    List<FlightPath> findByFlightLog(FlightLog flightLog);

    @Query("SELECT max(f.createTs) FROM FlightPath f WHERE f.flightLog.flightLogId = :flight_log_id")
    Optional<Timestamp> findNewestFlightPathEntry(@Param("flight_log_id") Integer flightLogId);

    @Query("SELECT f FROM FlightPath f WHERE f.createTs BETWEEN :start_date_time AND :end_date_time")
    List<FlightPath> findByDateTimeRange(
            @Param("start_date_time") LocalDateTime startDateTime,
            @Param("end_date_time") LocalDateTime endDateTime
    );
}
