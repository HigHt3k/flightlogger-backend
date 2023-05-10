package com.home_app.model.hue;

import java.util.Map;

public class LightsResponse {
    private Map<String, HueColorLamp> lights;

    public Map<String, HueColorLamp> getLights() {
        return lights;
    }

    public void setLights(Map<String, HueColorLamp> lights) {
        this.lights = lights;
    }
}
