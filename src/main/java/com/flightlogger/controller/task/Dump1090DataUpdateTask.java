package com.flightlogger.controller.task;

import com.flightlogger.service.Dump1090DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
public class Dump1090DataUpdateTask {

    private final Dump1090DataService dataService;
    private final Logger logger = LoggerFactory.getLogger(Dump1090DataUpdateTask.class);

    @Autowired
    public Dump1090DataUpdateTask(Dump1090DataService dataService) {
        this.dataService = dataService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void retrieveDump1090Data() {
        try {
            Socket socket = new Socket("localhost", 30003);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            logger.info("Starting to retrieve data from socket {} with port {}", socket.getInetAddress(), socket.getPort());

            String line;
            while ((line = reader.readLine()) != null) {
                // Process the received line and extract the necessary data
                // Parse the line and create a Dump1090Data object
                dataService.parseDump1090Data(line);
            }

            socket.close();
        } catch (IOException e) {
            // Handle any errors that occur while connecting or reading data from the socket
            logger.error(e.getMessage());
        }
    }
}
