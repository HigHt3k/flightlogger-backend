package com.home_app.controller;

import com.home_app.model.hue.HueColorLamp;
import com.home_app.model.weather.Weather;
import com.home_app.service.HueLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HueLightController {

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
}
