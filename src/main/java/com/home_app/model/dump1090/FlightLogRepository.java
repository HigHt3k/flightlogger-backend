package com.home_app.model.dump1090;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightLogRepository extends JpaRepository<FlightLog, Integer> {
    Optional<FlightLog> findById(Integer id);
    List<FlightLog> findAll();
    Optional<FlightLog> findByIcao24(Integer icao24);

    @Query("SELECT f FROM FlightLog f WHERE f.icao24 = :icao24 AND f.lastTs > :interval")
    Optional<FlightLog> findExistingFlight(@Param("icao24") Integer icao24, @Param("interval") Timestamp interval);
}
