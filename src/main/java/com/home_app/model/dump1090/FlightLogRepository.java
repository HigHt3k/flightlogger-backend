package com.home_app.model.dump1090;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightLogRepository extends JpaRepository<FlightLog, Integer> {
    Optional<FlightLog> findById(Integer id);
    List<FlightLog> findAll();
    Optional<FlightLog> findByIcao24(Integer icao24);
}
