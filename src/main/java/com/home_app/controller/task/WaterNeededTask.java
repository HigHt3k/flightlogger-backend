package com.home_app.controller.task;

import com.home_app.controller.PlantController;
import com.home_app.model.plant.Plant;
import com.home_app.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WaterNeededTask {

    @Autowired
    PlantService plantService;

    Logger logger = LoggerFactory.getLogger(WaterNeededTask.class);

    @Scheduled(cron = "0 */5 * * * *")
    public void checkIfWaterIsNeeded() {
        logger.info("Checking for plants that need a watering");
        List<Plant> plants = plantService.getPlants();
        plantService.checkIfWaterNeeded(plants);
        plantService.updatePlants(plants);
    }
}
