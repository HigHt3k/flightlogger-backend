package com.home_app.controller.task;

import com.home_app.model.planespotting.Aircraft;
import com.home_app.model.planespotting.AircraftRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AircraftDatabaseDownloader {

    Logger logger = LoggerFactory.getLogger(AircraftDatabaseDownloader.class);

    @Autowired
    private AircraftRepository aircraftRepository;

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

    @Scheduled(cron = "0 0 12 1 * ?") // This runs at 12 PM on the first day of every month
    @Transactional
    public void downloadCSVAndSaveToDB() {
        downloadCSV();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("aircraftDatabase.csv"));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

            List<Aircraft> aircrafts = new ArrayList<>();
            for (CSVRecord record : records) {
                Aircraft aircraft = new Aircraft();

                // Set aircraft properties based on CSV columns.
                // You may need to convert types and handle potential exceptions.
                aircraft.setAircraftRegistration(record.get(1)); // Assuming registration is the first column
                // ... set other fields ...

                aircrafts.add(aircraft);
            }

            aircraftRepository.saveAll(aircrafts);
            logger.info("Added a total of {} aircraft", aircrafts.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}