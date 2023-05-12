package com.home_app.controller;

import com.home_app.model.plant.Plant;
import com.home_app.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PlantController {

    Logger logger = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    private PlantService plantService;

    @GetMapping("/plants/all")
    public String getPlant(Model model) {
        try {
            List<Plant> plants = plantService.getPlants();
            model.addAttribute("plants", plants);
            return "allPlants";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/plants/add")
    public String addNewPlant(@RequestParam("plant") String name) {
        plantService.addPlant(name);
        logger.info("Added new plant: " + name);
        return "redirect:/plants/all";
    }

    @PostMapping("/plants/water/{id}")
    public String waterPlant(@PathVariable("id") Integer id) {
        Optional<Plant> optionalPlant = plantService.findById(id);
        if (optionalPlant.isPresent()) {
            Plant plant = optionalPlant.get();
            plant.setLastWatered(Timestamp.valueOf(LocalDateTime.now()));
            plantService.updatePlant(plant);
        }
        return "redirect:/plants/all";
    }
}
