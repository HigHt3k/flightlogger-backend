package com.home_app.service;

import com.home_app.model.plant.Plant;
import com.home_app.model.plant.PlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantService {

    Logger logger = LoggerFactory.getLogger(PlantService.class);

    @Autowired
    PlantRepository repository;

    public List<Plant> getPlants() {
        return repository.findAll();
    }

    public void addPlant(String name) {
        Plant plant = new Plant();
        plant.setName(name);
        repository.save(plant);
    }
    
}
