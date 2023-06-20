package com.home_app.service;

import com.home_app.model.planespotting.Sighting;
import com.home_app.model.planespotting.SightingImage;
import com.home_app.model.planespotting.SightingImageRepository;
import com.home_app.model.planespotting.SightingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
@PropertySource("classpath:application-dev.properties")
public class PlaneSpottingService {

    Logger logger = LoggerFactory.getLogger(PlaneSpottingService.class);

    @Autowired
    SightingRepository sightingRepository;

    @Autowired
    SightingImageRepository sightingImageRepository;

    public List<Sighting> getSightings() {
        return sightingRepository.findAll();
    }

    public List<SightingImage> getSightingImages() {
        return sightingImageRepository.findAll();
    }

    public void addSighting(Sighting sighting) {
        sightingRepository.save(sighting);
    }

    public void addSightingImage(SightingImage sightingImage) {
        sightingImageRepository.save(sightingImage);
    }

    public void removedSighting(Integer id) {
        Optional<Sighting> sighting = Objects.requireNonNull(sightingRepository.findById(id));
        sighting.ifPresent(value -> sightingRepository.delete(value));
    }
}
