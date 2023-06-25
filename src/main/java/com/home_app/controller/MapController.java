package com.home_app.controller;

import com.home_app.model.dump1090.FlightLog;
import com.home_app.model.dump1090.FlightLogRepository;
import com.home_app.model.dump1090.FlightPath;
import com.home_app.model.dump1090.FlightPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MapController {

    @Autowired
    private FlightLogRepository flightLogRepository;

    @Autowired
    private FlightPathRepository flightPathRepository;

    @GetMapping("/map")
    public String showMap(Model model) {
        List<FlightLog> flightLogList = flightLogRepository.findAll();
        List<FlightPath> flightPathList = flightPathRepository.findAll();
        model.addAttribute("flightLogs", flightLogList);
        model.addAttribute("flightPaths", flightPathList);
        return "map";
    }
}
