package com.home_app.model.planespotting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft ,Integer> {

    List<Aircraft> findAll();

    Optional<Aircraft> findById(Integer id);

    Optional<Aircraft> findByAircraftRegistration(String aircraftRegistration);

    @Query("SELECT DISTINCT a.operatorCode FROM Aircraft a")
    List<String> findDistinctOperators();
}
