package com.home_app.model.planespotting;

import com.home_app.model.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SightingRepository extends JpaRepository<Sighting ,Integer> {

    List<Sighting> findAll();

    Optional<Sighting> findById(Integer id);
}
