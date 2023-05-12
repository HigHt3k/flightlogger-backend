package com.home_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.home_app.model.hue.HueColorLamp;
import com.home_app.model.hue.LightsResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

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
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
        ResponseEntity<LightsResponse> response = restTemplate.getForEntity(url, LightsResponse.class);

        Map<String, HueColorLamp> lights = Objects.requireNonNull(response.getBody()).getLights();
        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Response retrieved from Philips Hue API with response code " + response.getStatusCode());
            return lights;
        } else {
            throw new RuntimeException("Failed to fetch philips hue data");
        }
    }

    public void toggleLamp(int id) {
        Map<String, HueColorLamp> lamps = getColorLamps();
        if(lamps.containsKey(String.valueOf(id))) {
            String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
            url += "/lights/" + String.valueOf(id) + "/state";

            String messageBody = "{\"on\":";
            if(lamps.get(String.valueOf(id)).getState().isOn()) {
                messageBody += "false}";
            } else {
                messageBody += "true}";
            }

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPut httpPut = new HttpPut(url);

                httpPut.setEntity(new StringEntity(messageBody));
                logger.info("Executing PUT request...");
                HttpResponse response = httpClient.execute(httpPut);
                logger.info("Status code: " + response.getStatusLine().getStatusCode());
                String responseBody = new BasicResponseHandler().handleResponse(response);
                logger.info(responseBody);
            } catch (IOException e) {
                throw new RuntimeException("Can't execute statement");
            }
        } else {
            throw new RuntimeException("lamp not available");
        }
    }
}
