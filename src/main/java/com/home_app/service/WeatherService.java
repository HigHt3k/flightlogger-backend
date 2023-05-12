package com.home_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.home_app.model.weather.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")
@PropertySource("classpath:application-dev.properties")
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

    @Value("${weather.api.url}")
    private String API_URL;

    public Weather getWeatherByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{city}", city).replace("{apiKey}", API_KEY);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            JsonNode body = response.getBody();
            Weather weather = new Weather();
            weather.setCityName(body.get("name").asText());
            weather.setTemperature(body.get("main").get("temp").asDouble());
            weather.setDescription(body.get("weather").get(0).get("description").asText());
            return weather;
        } else {
            throw new RuntimeException("Failed to fetch weather data");
        }
    }
}
