package com.home_app.model.dump1090;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightLogRepository extends JpaRepository<FlightLog, Integer> {
    Optional<FlightLog> findById(Integer id);
    List<FlightLog> findAll();

    @Query("SELECT f FROM FlightLog f WHERE f.aircraft.icao24 = :icao24 AND f.lastTs > :interval")
    Optional<FlightLog> findExistingFlight(@Param("icao24") Integer icao24, @Param("interval") Timestamp interval);

    @Query("SELECT f FROM FlightLog f WHERE f.lastTs BETWEEN :start_date_time AND :end_date_time")
    List<FlightLog> findByDateTimeRange(
            @Param("start_date_time") LocalDateTime startDateTime,
            @Param("end_date_time") LocalDateTime endDateTime
    );
}
