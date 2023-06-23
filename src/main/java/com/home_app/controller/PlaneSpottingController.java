package com.home_app.controller;

import com.home_app.controller.task.AircraftDatabaseDownloader;
import com.home_app.model.planespotting.Sighting;
import com.home_app.model.planespotting.SightingImage;
import com.home_app.model.plant.Plant;
import com.home_app.service.PlaneSpottingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PlaneSpottingController {

    Logger logger = LoggerFactory.getLogger(PlaneSpottingService.class);

    @Autowired
    PlaneSpottingService planeSpottingService;

    private final AircraftDatabaseDownloader aircraftDatabaseDownloader;

    @Autowired
    public PlaneSpottingController(AircraftDatabaseDownloader aircraftDatabaseDownloader) {
        this.aircraftDatabaseDownloader = aircraftDatabaseDownloader;
    }

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
                                @RequestParam String spottedAtICAO,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
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

    @GetMapping("/planespotting/aircraft-master-data/update")
    public String updateAircraftMasterData() {
        aircraftDatabaseDownloader.downloadCSVAndSaveToDB();
        return "redirect:/planespotting/sightings";
    }

    @PostMapping("/planespotting/sightings/upload")
    public String uploadSightingImage(@RequestParam Integer sightingId,
                                      @RequestParam("file") MultipartFile file) {
        Optional<Sighting> sighting = planeSpottingService.getSightingById(sightingId);
        if(sighting.isPresent()) {
            SightingImage sightingImage = new SightingImage();

            // Generate a unique filename for the uploaded file
            String filename = UUID.randomUUID().toString() + ".jpg";

            // Define the directory where the files will be stored
            String uploadDirectory = "planespotting/images/";

            // Create the directory if it doesn't exist
            try {
                Path directoryPath = Paths.get(uploadDirectory);
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                // Handle the exception, e.g., log an error message or throw a custom exception
                e.printStackTrace();
            }

            // Construct the full file path
            String filePath = uploadDirectory + filename;

            // Save the file to the server's file system
            Path path = Paths.get(filePath);
            try {
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                logger.error("Can't write file.", e);
            }

            // Set the image path to the file path
            sightingImage.setImagePath(filePath);
            sightingImage.setSighting(sighting.get());
            planeSpottingService.addSightingImage(sightingImage);
            logger.info("Successfully added image(s) to sighting.");
        }

        return "redirect:/planespotting/sightings";
    }
}
