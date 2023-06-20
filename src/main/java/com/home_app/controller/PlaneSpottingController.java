package com.home_app.controller;

import com.home_app.model.planespotting.Sighting;
import com.home_app.model.plant.Plant;
import com.home_app.service.PlaneSpottingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PlaneSpottingController {

    Logger logger = LoggerFactory.getLogger(PlaneSpottingService.class);

    @Autowired
    PlaneSpottingService planeSpottingService;

    @GetMapping("/planespotting/sightings")
    public String getSightings(Model model) {
        try {
            List<Sighting> sightings = planeSpottingService.getSightings();
            model.addAttribute("sightings", sightings);
            return "sightings";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
