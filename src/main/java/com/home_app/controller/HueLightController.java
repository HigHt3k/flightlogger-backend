package com.home_app.controller;

import com.home_app.model.hue.HueColorLamp;
import com.home_app.model.hue.HueMotionSensor;
import com.home_app.model.plant.Plant;
import com.home_app.model.weather.Weather;
import com.home_app.service.HueLightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HueLightController {

    Logger logger = LoggerFactory.getLogger(HueLightController.class);

    @Autowired
    private HueLightService hueLightService;

    @GetMapping("/lights")
    public String getLights(Model model) {
        try {
            Map<String, HueColorLamp> colorLampMap = hueLightService.getColorLamps();
            List<HueColorLamp> colorLamps = new ArrayList<>(colorLampMap.values());
            model.addAttribute("lights", colorLamps);
            return "light";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch hue data");
            return "error";
        }
    }

    @PostMapping("/lights/light/state/{id}")
    public String toggleLight(@PathVariable("id") Integer id) {
        hueLightService.toggleLamp(id);
        return "redirect:/lights";
    }

    @PostMapping("/lights/light/color/{id}")
    public String changeLightColor(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> requestBody) {
        String color = requestBody.get("color");
        // Process the color value and perform the desired action
        hueLightService.changeLampColor(id, color);
        return "redirect:/lights";
    }
}
