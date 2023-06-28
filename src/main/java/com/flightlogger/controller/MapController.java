package com.flightlogger.controller;

import com.flightlogger.model.dump1090.FlightLog;
import com.flightlogger.model.dump1090.FlightLogRepository;
import com.flightlogger.model.dump1090.FlightPath;
import com.flightlogger.model.dump1090.FlightPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
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
