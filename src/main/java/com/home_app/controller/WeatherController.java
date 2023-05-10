package com.home_app.controller;

import com.home_app.model.weather.Weather;
import com.home_app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        try {
            Weather weather = weatherService.getWeatherByCity(city);
            model.addAttribute("weather", weather);
            return "weather";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch weather data for " + city);
            return "error";
        }
    }
}
