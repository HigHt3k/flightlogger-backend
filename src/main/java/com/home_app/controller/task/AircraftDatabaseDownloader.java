package com.home_app.controller.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class AircraftDatabaseDownloader {

    @Scheduled(cron = "0 0 12 1 * ?") // This runs at 12 PM on the first day of every month
    public void downloadCSV() {
        String baseUrl = "https://openskynetwork.org/datasets/aircraftDatabase/aircraft-database-complete-";
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String url = baseUrl + datePart + ".csv";

        try {
            RestTemplate restTemplate = new RestTemplate();
            ReadableByteChannel rbc = Channels.newChannel(Objects.requireNonNull(restTemplate.getForObject(url, InputStream.class)));
            FileOutputStream fos = new FileOutputStream("aircraftDatabase.csv");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}