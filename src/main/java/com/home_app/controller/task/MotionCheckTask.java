package com.home_app.controller.task;

import com.home_app.model.hue.HueColorLamp;
import com.home_app.model.hue.HueMotionSensor;
import com.home_app.model.plant.Plant;
import com.home_app.service.HueLightService;
import com.home_app.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Deprecated
public class MotionCheckTask {

    /*
    String updateTs = "";

    @Autowired
    HueLightService hueLightService;

    Logger logger = LoggerFactory.getLogger(MotionCheckTask.class);

    @Scheduled(fixedRate = 500)
    public void checkMotion() {
        Map<String, HueMotionSensor> sensors = hueLightService.getMotionSensors();

        for (HueMotionSensor sensor : sensors.values()) {
            if (sensor.getState().isPresence() && !sensor.getState().getLastupdated().equals(updateTs)) {
                logger.info("Motion detected by sensor: " + sensor.getName() + ": " + sensor.getState().getLastupdated());
                updateTs = sensor.getState().getLastupdated();
                turnOnHallway();
            }
        }
    }

    private void turnOnHallway() {
        Thread thread = new Thread(() -> {
            Map<String, HueColorLamp> lamps = hueLightService.getColorLamps();
            for(Map.Entry<String, HueColorLamp> entry : lamps.entrySet()) {
                if(entry.getValue().getName().contains("Hallway")) {
                    hueLightService.setLampOn(Integer.parseInt(entry.getValue().getLightid()));
                }
            }
            try {
                Thread.sleep(5000*60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(Map.Entry<String, HueColorLamp> entry : lamps.entrySet()) {
                if(entry.getValue().getName().contains("Hallway")) {
                    hueLightService.setLampOff(Integer.parseInt(entry.getValue().getLightid()));
                }
            }
        });
        thread.start();
    }

     */
}
