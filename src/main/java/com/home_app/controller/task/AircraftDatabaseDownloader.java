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

    Logger logger = LoggerFactory.getLogger(AircraftDatabaseDownloader.class);

    @Autowired
    private AircraftRepository aircraftRepository;

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

            List<Aircraft> aircraftsToAdd = new ArrayList<>();
            List<Aircraft> aircraftsToUpdate = new ArrayList<>();
            List<Aircraft> existingAircraftRegistrations = aircraftRepository.findAll();

            // Iterate over the records
            for (String[] record : records) {
                try {
                    // Set aircraft properties based on CSV columns.
                    // You may need to convert types and handle potential exceptions.
                    String icao24 = record[0].replaceAll("\"", "");
                    String aircraftRegistration = record[1].replaceAll("\"", "");
                    String aircraftManufacturer = record[2].replaceAll("\"", "");
                    String aircraftManufacturerSecondary = record[3].replaceAll("\"", "");
                    String aircraftType = record[4].replaceAll("\"", "");
                    String aircraftTypeShort = record[5].replaceAll("\"", "");
                    String information1 = record[6].replaceAll("\"", "");
                    String information2 = record[7].replaceAll("\"", "");
                    String information3 = record[8].replaceAll("\"", "");
                    String information4 = record[9].replaceAll("\"", "");
                    String operatorShort = record[10].replaceAll("\"", "");
                    String operatorCode = record[11].replaceAll("\"", "");
                    String operator = record[12].replaceAll("\"", "");
                    String information5 = record[13].replaceAll("\"", "");

                    Optional<Aircraft> existingAircraft = aircraftRepository.findByAircraftRegistration(aircraftRegistration);
                    if (existingAircraft.isPresent()) {
                        // Check if the existing aircraft has different values
                        if (!existingAircraft.get().getIcao24().equals(icao24) ||
                                !existingAircraft.get().getAircraftManufacturer().equals(aircraftManufacturer) ||
                                !existingAircraft.get().getAircraftType().equals(aircraftType) ||
                                !existingAircraft.get().getOperator().equals(operator) ||
                                !existingAircraft.get().getOperatorCode().equals(operatorCode)) {
                            // Update the existing aircraft
                            existingAircraft.get().setIcao24(icao24);
                            existingAircraft.get().setAircraftManufacturer(aircraftManufacturer);
                            existingAircraft.get().setAircraftType(aircraftType);
                            existingAircraft.get().setOperator(operator);
                            existingAircraft.get().setOperatorCode(operatorCode);
                            aircraftsToUpdate.add(existingAircraft.get());
                        }
                    } else {
                        // Create a new aircraft
                        Aircraft newAircraft = new Aircraft();
                        newAircraft.setIcao24(icao24);
                        newAircraft.setAircraftRegistration(aircraftRegistration);
                        newAircraft.setAircraftManufacturer(aircraftManufacturer);
                        newAircraft.setAircraftType(aircraftType);
                        newAircraft.setOperator(operator);
                        newAircraft.setOperatorCode(operatorCode);
                        aircraftsToAdd.add(newAircraft);
                    }
                    // Remove the aircraft registration from the list of existing registrations
                    existingAircraftRegistrations.remove(aircraftRegistration);
                } catch (IllegalStateException e) {
                    logger.error("Error processing record: " + Arrays.toString(record) + " at line " + records.indexOf(record), e);
                }
            }

            // Delete the aircrafts with the remaining aircraft registrations
            for (Aircraft aircraft : existingAircraftRegistrations) {
                Optional<Aircraft> aircraftToDelete = aircraftRepository.findByAircraftRegistration(aircraft.getAircraftRegistration());
                if(aircraftToDelete.isPresent()) {
                    aircraftRepository.delete(aircraft);
                }
            }

            // Save the new aircrafts and update the existing aircrafts
            aircraftRepository.saveAll(aircraftsToAdd);
            aircraftRepository.saveAll(aircraftsToUpdate);

            logger.info("Added a total of {} aircraft and updated a total of {} aircraft",
                    aircraftsToAdd.size(), aircraftsToUpdate.size());
        } catch (IOException e) {
            logger.error("Error while reading the CSV file", e);
        } catch (CsvException e) {
            logger.error("CSV Error", e);
        }
    }

}