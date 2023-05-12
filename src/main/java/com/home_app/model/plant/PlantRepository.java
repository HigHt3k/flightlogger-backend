package com.home_app.model.plant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {

    List<Plant> findAll();

    Optional<Plant> findById(Integer id);
}
