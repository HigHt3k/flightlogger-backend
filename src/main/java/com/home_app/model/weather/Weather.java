package com.home_app.model.weather;

public class Weather {
    private String cityName;
    private double temperature;
    private String description;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }
}
