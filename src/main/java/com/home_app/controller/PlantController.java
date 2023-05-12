package com.home_app.controller;

import com.home_app.model.plant.HousePlant;
import com.home_app.model.plant.PlantRepository;
import com.home_app.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PlantController {

    Logger logger = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    private PlantService plantFinderService;

    @GetMapping("/plants")
    public String getPlant(Model model) {
        try {
            List<HousePlant> plants = plantFinderService.getPlants();
            logger.info("Found a total of " + plants.size() + " entries in plants database.");
            model.addAttribute("plants", plants);
            return "plants";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
