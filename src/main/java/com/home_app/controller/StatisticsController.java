package com.home_app.controller;

import com.home_app.model.planespotting.Aircraft;
import com.home_app.model.planespotting.Sighting;
import com.home_app.service.PlaneSpottingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class StatisticsController {

    @Autowired
    private PlaneSpottingService planeSpottingService;

    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @GetMapping("/planespotting/statistics")
    public String showStatistics(Model model) {
        /*
        // Retrieve the necessary data for the statistics
        List<Sighting> sightings = planeSpottingService.getAllSightings();
        List<Aircraft> aircrafts = planeSpottingService.getAllAircrafts();
        List<String> operators = planeSpottingService.getAllOperators();

        // Calculate the statistics
        int uniqueAircraftCount = calculateUniqueAircraftCount(sightings);
        Map<String, Integer> aircraftSightingCountMap = calculateAircraftSightingCount(sightings);
        Map<String, Double> operatorFleetSpottedPercentageMap = calculateOperatorFleetSpottedPercentage(operators, aircrafts, sightings);

        // Add the statistics data to the model
        model.addAttribute("uniqueAircraftCount", uniqueAircraftCount);
        model.addAttribute("aircraftSightingCountMap", aircraftSightingCountMap);
        model.addAttribute("operatorFleetSpottedPercentageMap", operatorFleetSpottedPercentageMap);

         */
        return "statistics";
    }


    // Helper methods to calculate the statistics
    private int calculateUniqueAircraftCount(List<Sighting> sightings) {
        Set<Aircraft> uniqueAircraftSet = new HashSet<>();

        for (Sighting sighting : sightings) {
            Aircraft aircraft = sighting.getAircraft();
            uniqueAircraftSet.add(aircraft);
        }

        return uniqueAircraftSet.size();
    }

    private Map<String, Integer> calculateAircraftSightingCount(List<Sighting> sightings) {
        Map<String, Integer> aircraftSightingCountMap = new HashMap<>();

        for (Sighting sighting : sightings) {
            Aircraft aircraft = sighting.getAircraft();
            int count = aircraftSightingCountMap.getOrDefault(aircraft.getAircraftRegistration(), 0);
            aircraftSightingCountMap.put(aircraft.getAircraftRegistration(), count + 1);
        }

        return aircraftSightingCountMap;
    }

    private Map<String, Double> calculateOperatorFleetSpottedPercentage(List<String> operators, List<Aircraft> aircrafts, List<Sighting> sightings) {
        Map<String, Double> operatorFleetSpottedPercentageMap = new HashMap<>();

        logger.info("Found a total of {} operators", operators.size());

        for (String operator : operators) {
            int operatorFleetSize = 0;
            int operatorSpottedCount = 0;

            for (Aircraft aircraft : aircrafts) {
                if (aircraft.getOperatorCode().equals(operator)) {
                    operatorFleetSize++;
                }
            }

            for (Sighting sighting : sightings) {
                if(sighting.getAircraft().getOperatorCode().equals(operator)) {
                    operatorSpottedCount++;
                }
            }

            double spottedPercentage = (double) operatorSpottedCount / operatorFleetSize * 100;
            if(operatorSpottedCount > 0) {
                operatorFleetSpottedPercentageMap.put(operator, spottedPercentage);
            }
        }

        return operatorFleetSpottedPercentageMap;
    }

}
