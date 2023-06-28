package com.home_app.controller;

import com.home_app.model.dump1090.FlightLog;
import com.home_app.model.dump1090.FlightLogRepository;
import com.home_app.model.dump1090.FlightPath;
import com.home_app.model.dump1090.FlightPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/map")
    public ResponseEntity<Map<String, Object>> getMapData(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime
            ) {
        List<FlightLog> flightLogList = flightLogRepository.findByDateTimeRange(startDateTime, endDateTime);
        List<FlightPath> flightPathList = flightPathRepository.findByDateTimeRange(startDateTime, endDateTime);

        Map<String, Object> mapData = new HashMap<>();
        mapData.put("flightLogs", flightLogList);
        mapData.put("flightPaths", flightPathList);

        return ResponseEntity.ok(mapData);
    }
}
