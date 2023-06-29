package com.flightlogger.controller;

import com.flightlogger.model.dump1090.FlightLog;
import com.flightlogger.model.dump1090.FlightLogRepository;
import com.flightlogger.model.dump1090.FlightPath;
import com.flightlogger.model.dump1090.FlightPathRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    private final Logger logger = LoggerFactory.getLogger(MapController.class);

    @Autowired
    private FlightLogRepository flightLogRepository;

    @Autowired
    private FlightPathRepository flightPathRepository;

    @GetMapping("/map")
    public ResponseEntity<List<FlightLog>> getMapData(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime
            ) {
        List<FlightLog> flightLogList = flightLogRepository.findByDateTimeRange(Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime));
        logger.info(flightLogList.get(0).getFlightPaths().get(0).getFlightPathId().toString());
        return ResponseEntity.ok(flightLogList);
    }
}
