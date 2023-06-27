package com.home_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.home_app.model.hue.HueColorLamp;
import com.home_app.model.hue.HueMotionSensor;
import com.home_app.model.hue.LightsResponse;
import com.home_app.model.hue.SensorsResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.util.*;

@Service
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

        for(Map.Entry<String, HueColorLamp> entry : lights.entrySet()) {
            String id = entry.getKey();
            HueColorLamp lamp = entry.getValue();
            lamp.setLightid(id);

            lamp.setColorCode(HSBtoHexColorCode(lamp.getState().getHue(), lamp.getState().getSat(), lamp.getState().getBri()));
        }

        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Response retrieved from Philips Hue API with response code " + response.getStatusCode());
            return lights;
        } else {
            throw new RuntimeException("Failed to fetch philips hue data");
        }
    }

    public Map<String, HueMotionSensor> getMotionSensors() {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
        ResponseEntity<SensorsResponse> response = restTemplate.getForEntity(url, SensorsResponse.class);

        Map<String, HueMotionSensor> sensors = Objects.requireNonNull(response.getBody()).getSensors();

        if(response.getStatusCode() == HttpStatus.OK) {
            return sensors;
        } else {
            throw new RuntimeException("Failed to fetch philips hue sensor data");
        }
    }

    private String HSBtoHexColorCode(int hue, int saturation, int brightness) {
        float hueConverted = hue/65535f;
        float saturationConverted = saturation/255f;
        float brightnessConverted = brightness/255f;

        Color color = Color.getHSBColor(hueConverted, saturationConverted, brightnessConverted);
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public void toggleLamp(int id) {
        Map<String, HueColorLamp> lamps = getColorLamps();
        if(lamps.containsKey(String.valueOf(id))) {
            String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
            url += "/lights/" + id + "/state";

            String messageBody = "{\"on\":";
            if(lamps.get(String.valueOf(id)).getState().isOn()) {
                messageBody += "false}";
            } else {
                messageBody += "true}";
            }

            executePutRequest(url, messageBody);
        } else {
            throw new RuntimeException("lamp not available");
        }
    }

    public void setLampOn(int id) {
        Map<String, HueColorLamp> lamps = getColorLamps();
        if(lamps.containsKey(String.valueOf(id))) {
            String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
            url += "/lights/" + id + "/state";

            String messageBody = "{\"on\":";
            messageBody += "true}";

            executePutRequest(url, messageBody);
        } else {
            throw new RuntimeException("lamp not available");
        }
    }

    public void setLampOff(int id) {
        Map<String, HueColorLamp> lamps = getColorLamps();
        if(lamps.containsKey(String.valueOf(id))) {
            String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
            url += "/lights/" + id + "/state";

            String messageBody = "{\"on\":";
            messageBody += "false}";

            executePutRequest(url, messageBody);
        } else {
            throw new RuntimeException("lamp not available");
        }
    }

    /**
     * Change the lamp color.
     * @param id: id of the lamp
     * @param colorString: hex code of the color
     */
    public void changeLampColor(int id, String colorString) {
        logger.info("Change color of lamp {} to {}", id, colorString);

        Map<String, HueColorLamp> lamps = getColorLamps();
        if(lamps.containsKey(String.valueOf(id))) {
            String url = API_URL.replace("{HUE_BRIDGE_IP}", HUE_BRIDGE_IP).replace("{API_KEY}", API_KEY);
            url += "/lights/" + id + "/state";

            // calculate color values
            Color color = Color.decode(colorString);
            float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

            int hue = (int) (hsb[0] * 65535);
            int saturation = (int) (hsb[1] * 255);
            int brightness = (int) (hsb[2] * 255);

            logger.info("Calculated Color:\nHue: {}, Saturation: {}, Brightness: {}", hue, saturation, brightness);

            String messageBody = "{\"bri\":" + brightness + ",\"sat\":" + saturation + ",\"hue\":" + hue + "}";

            executePutRequest(url, messageBody);
        } else {
            throw new RuntimeException("lamp not available");
        }
    }

    private void executePutRequest(String url, String messageBody) {
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
    }
}
