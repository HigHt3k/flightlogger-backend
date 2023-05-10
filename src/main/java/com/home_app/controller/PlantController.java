package com.home_app.controller;

import com.home_app.model.plant.Plant;
import com.home_app.service.PlantFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlantController {

    Logger logger = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    private PlantFinderService plantFinderService;

    @GetMapping("/plants")
    public String getPlant(@RequestParam("plantKeyword") String plantKeyword, Model model) {
        logger.info("Requesting a plant with keyword " + plantKeyword);
        try {
            Plant plant = plantFinderService.getPlantByKeyword(plantKeyword);
            model.addAttribute("plant", plant);
            return "plants";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
