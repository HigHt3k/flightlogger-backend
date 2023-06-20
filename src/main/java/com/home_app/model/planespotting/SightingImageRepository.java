package com.home_app.model.planespotting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SightingImageRepository extends JpaRepository<SightingImage, Integer> {

    @Override
    List<SightingImage> findAll();

    Optional<SightingImage> findById(Integer id);
}
