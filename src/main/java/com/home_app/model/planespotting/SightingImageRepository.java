package com.home_app.model.planespotting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SightingImageRepository extends JpaRepository<SightingImage, Integer> {

    List<SightingImage> findAll();

    Optional<SightingImage> findById(Integer id);
}
