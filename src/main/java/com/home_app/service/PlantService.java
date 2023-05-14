package com.home_app.service;

import com.home_app.model.plant.Plant;
import com.home_app.model.plant.PlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
@PropertySource("classpath:application-dev.properties")
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

    public Optional<Plant> findById(Integer id) {
        return repository.findById(id);
    }

    public void updatePlant(Plant plant) {
        calculateNextWateringTimestamp(plant);
        repository.save(plant);
    }

    public void updatePlants(List<Plant> plants) {
        repository.saveAll(plants);
    }

    public void calculateNextWateringTimestamp(Plant plant) {
        Optional<Integer> wateringCycle = Optional.ofNullable(plant.getWateringCycle());
        Optional<Timestamp> lastWatered = Optional.ofNullable(plant.getLastWatered());

        if(lastWatered.isPresent() && wateringCycle.isPresent()) {
            plant.setNextWatering(new Timestamp(lastWatered.get()
                    .getTime() + (1000L * 60 * 60 * 24 * wateringCycle.get())));
        }

        checkIfWaterNeeded(plant);
    }

    public void checkIfWaterNeeded(List<Plant> plants) {
        Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());
        for(Plant plant : plants) {
            Optional<Timestamp> nextWatering = Optional.ofNullable(plant.getNextWatering());
            if(nextWatering.isPresent()) {
                plant.setWaterNeeded(plant.getNextWatering().before(currentDateTime));
            } else {
                plant.setWaterNeeded(false);
            }
        }
    }

    public void checkIfWaterNeeded(Plant plant) {
        Timestamp currentDateTime = Timestamp.valueOf(LocalDateTime.now());

        Optional<Timestamp> nextWatering = Optional.ofNullable(plant.getNextWatering());
        if(nextWatering.isPresent()) {
            plant.setWaterNeeded(plant.getNextWatering().before(currentDateTime));
        } else {
            plant.setWaterNeeded(false);
        }
    }
}
