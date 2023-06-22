package com.home_app.controller;

import com.home_app.model.planespotting.Sighting;
import com.home_app.model.plant.Plant;
import com.home_app.service.PlaneSpottingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
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
            logger.info("Found a total of {} sightings", sightings.size());
            model.addAttribute("sightings", sightings);
            return "sightings";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/planespotting/sightings/add")
    public String addSighting(@RequestParam String aircraftRegistrations,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              @RequestParam String spottedAtICAO) {
        logger.info("Received POST request with name: " + aircraftRegistrations + ", date: " + date);

        List<Sighting> sightings = new ArrayList<>();
        List<String> registrations = List.of(aircraftRegistrations.split(","));

        for(String registration : registrations) {
            Sighting sighting = new Sighting();
            sighting.setAircraftRegistration(registration);
            sighting.setSightingDate(Timestamp.valueOf(date.atStartOfDay()));
            sighting.setSpottedAtICAO(spottedAtICAO);
            sightings.add(sighting);
        }

        planeSpottingService.addSightings(sightings);

        return "redirect:/planespotting/sightings";
    }
}
