package com.home_app.model.hue;

import java.util.Map;

public class SensorsResponse {
    public Map<String, HueMotionSensor> getSensors() {
        return sensors;
    }

    public void setSensors(Map<String, HueMotionSensor> sensors) {
        this.sensors = sensors;
    }

    private Map<String, HueMotionSensor> sensors;

    // Getter and setter...
}