package com.home_app.controller.task;

import com.home_app.exceptions.InvalidAircraftRegistrationException;
import com.home_app.model.planespotting.Aircraft;
import com.home_app.model.planespotting.AircraftRepository;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class AircraftDatabaseDownloader {

    private final AircraftRepository aircraftRepository;
    private final Logger logger = LoggerFactory.getLogger(AircraftDatabaseDownloader.class);

    @Autowired
    public AircraftDatabaseDownloader(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public void downloadCSV() {
        String baseUrl = "https://opensky-network.org/datasets/metadata/aircraft-database-complete-";
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String url = baseUrl + datePart + ".csv";
        String filePath = "aircraft-master-data.csv";

        logger.info("Starting download from {}", url);

        try {
            File file = new File(filePath);
            if(file.exists()) {
                Instant end = Instant.now();
                Instant start = Instant.ofEpochMilli(file.lastModified());
                Duration timeElapsed = Duration.between(start, end);

                // Checking if the existing file is older than 2 days
                if(timeElapsed.toDays() <= 2) {
                    logger.info("Data is up to date, no need to download again");
                    return;
                }
            }

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                // This will overwrite the file if it exists
                Files.write(Paths.get(filePath), Objects.requireNonNull(responseEntity.getBody()), StandardOpenOption.CREATE);
            } else {
                throw new Exception("Error while downloading the file. Server responded with status: "+responseEntity.getStatusCode());
            }

            logger.info("Finished download.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Scheduled(cron = "0 0 12 1 * ?") // This runs at 12 PM on the first day of every month
    @Transactional
    public void downloadCSVAndSaveToDB() {
        downloadCSV();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("aircraft-master-data.csv"), StandardCharsets.ISO_8859_1);
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0) // Skip header line if present
                    .build();

            List<String[]> records = csvReader.readAll();
            List<Aircraft> aircrafts = new ArrayList<>();

            int progress = 0;

            // Iterate over the records
            for (String[] record : records) {
                if(progress % 500 == 0) {
                    logger.info("Progress: {}/{}", progress, records.size());
                }
                progress++;
                try {
                    Aircraft aircraft = new Aircraft();

                    // Set aircraft properties based on CSV columns.
                    if(record[0].equals("") || record[0].isEmpty()) {
                        throw new IllegalStateException("primary key value (icao24) is empty, skipping.");
                    }
                    Integer icao24 = Integer.parseInt(record[0], 16);
                    String aircraftRegistration = record[1];
                    String aircraftManufacturer = record[2];
                    String aircraftManufacturerSecondary = record[3];
                    String aircraftType = record[4];
                    String aircraftTypeShort = record[5];
                    String information1 = record[6];
                    String information2 = record[7];
                    String information3 = record[8];
                    String information4 = record[9];
                    String operatorShort = record[10];
                    String operatorCode = record[11];
                    String operator = record[12];
                    String information5 = record[13];

                    if (aircraftRegistration == null || aircraftRegistration.isEmpty() || aircraftRegistration.equals("\"\"")) {
                        throw new InvalidAircraftRegistrationException("Invalid aircraft registration");
                    }
                    aircraft.setIcao24(icao24);
                    aircraft.setAircraftRegistration(aircraftRegistration.replaceAll("\"", ""));
                    aircraft.setAircraftManufacturer(aircraftManufacturer.replaceAll("\"", ""));
                    aircraft.setAircraftType(aircraftType.replaceAll("\"", ""));
                    aircraft.setOperator(operator.replaceAll("\"", ""));
                    aircraft.setOperatorCode(operatorCode.replaceAll("\"", ""));

                    aircrafts.add(aircraft);
                } catch (IllegalStateException e) {
                    logger.error("Error processing record: " + Arrays.toString(record) + " at line " + records.indexOf(record), e);
                    continue;
                } catch (InvalidAircraftRegistrationException e) {
                    continue;
                }
            }

            aircraftRepository.saveAll(aircrafts);
            logger.info("Added a total of {} aircraft", aircrafts.size());
        } catch (IOException e) {
            logger.error("Error while reading the CSV file", e);
        } catch (CsvException e) {
            logger.error("CSV Error", e);
        }
    }

}