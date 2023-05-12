package com.home_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.home_app.model.hue.HueColorLamp;
import com.home_app.model.hue.LightsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@PropertySource("classpath:application.properties")
@PropertySource("classpath:application-dev.properties")
public class HueLightService {

    Logger logger = LoggerFactory.getLogger(HueLightService.class);

    @Value("${hue.bridge.ip}")
    private String HUE_BRIDGE_IP;

    @Value("${hue.api.url}")
    private String API_URL;

    @Value("${hue.api.key}")
    private String API_KEY;

    public Map<String, HueColorLamp> getColorLamps() {
        List<HueColorLamp> colorLamps = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
        ResponseEntity<LightsResponse> response = restTemplate.getForEntity(url, LightsResponse.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Response retrieved from Philips Hue API with response code " + response.getStatusCode());
            return Objects.requireNonNull(response.getBody()).getLights();
        } else {
            throw  new RuntimeException("Failed to fetch philips hue data");
        }
    }
}
