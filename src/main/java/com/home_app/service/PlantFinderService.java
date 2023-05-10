package com.home_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.home_app.model.plant.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class PlantFinderService {

    Logger logger = LoggerFactory.getLogger(PlantFinderService.class);

    @Value("${plants.api.key}")
    private String API_KEY;

    @Value("${plants.api.url}")
    private String API_URL;

    public Plant getPlantByKeyword(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{API_KEY}", API_KEY).replace("{KEYWORD}", keyword);
        logger.info("Using url to get plant: " + url);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Plant data fetched with status code " + response.getStatusCode());
            JsonNode body = response.getBody();

            Plant plant = new Plant();
            // TODO: create list of results, for now only showing first entry!
            plant.setCommon_name(body.get("data").get(0).get("common_name").asText());
            plant.setCycle(body.get("data").get(0).get("cycle").asText());
            plant.setSunlight(body.get("data").get(0).get("sunlight").asText());
            plant.setWatering(body.get("data").get(0).get("watering").asText());
            return plant;
        } else {
            throw new RuntimeException("Plant data not fetched successfully");
        }
    }
}
