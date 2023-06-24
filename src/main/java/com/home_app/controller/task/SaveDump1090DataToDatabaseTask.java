package com.home_app.controller.task;

import com.home_app.model.dump1090.Dump1090Data;
import com.home_app.model.dump1090.FlightLog;
import com.home_app.model.dump1090.FlightLogRepository;
import com.home_app.model.dump1090.MessageLogRepository;
import com.home_app.service.Dump1090DataQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class SaveDump1090DataToDatabaseTask {

    private final Logger logger = LoggerFactory.getLogger(SaveDump1090DataToDatabaseTask.class);

    private Dump1090DataQueue dataQueue;

    private MessageLogRepository messageLogRepository;
    private FlightLogRepository flightLogRepository;

    @Autowired
    public SaveDump1090DataToDatabaseTask(Dump1090DataQueue dataQueue,
                                          MessageLogRepository messageLogRepository,
                                          FlightLogRepository flightLogRepository) {
        this.dataQueue = dataQueue;
        this.messageLogRepository = messageLogRepository;
        this.flightLogRepository = flightLogRepository;
    }

    @Scheduled(fixedRate = 1000)
    public void saveDataToDatabase() {
        int currentQueueSize = dataQueue.currentQueueSize();
        logger.info("Writing a total of {} messages to the database.", currentQueueSize);
        for(int i = 0; i < currentQueueSize; i++) {
            Dump1090Data data = dataQueue.getNextData();
            messageLogRepository.save(data);
            updateFlightLog(data);
        }
    }

    private void updateFlightLog(Dump1090Data data) {
        int icao24 = data.getIcao24Decimal();
        Optional<FlightLog> flightLog = flightLogRepository.findByIcao24(icao24);
        if(flightLog.isEmpty()) {
            FlightLog newFlightLog = new FlightLog();
            newFlightLog.setIcao24(icao24);
            newFlightLog.setFirstAltitude(data.getAltitude());
            newFlightLog.setLastAltitude(data.getAltitude());
            newFlightLog.setFirstLatitude(data.getLatitude());
            newFlightLog.setFirstLongitude(data.getLongitude());
            newFlightLog.setLastLatitude(data.getLatitude());
            newFlightLog.setLastLongitude(data.getLongitude());
            newFlightLog.setEmergencyFlag(data.getEmergencyFlag());
            newFlightLog.setCallsign(data.getCallsign());

            if(data.getDateMessageGenerated() != null && data.getTimeMessageGenerated() != null) {
                Timestamp timestamp = Timestamp.valueOf(data.getDateMessageGenerated().replace("/", "-")
                        + " " + data.getTimeMessageGenerated());
                newFlightLog.setFirstTs(timestamp);
                newFlightLog.setLastTs(timestamp);
            } else {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                newFlightLog.setFirstTs(timestamp);
                newFlightLog.setLastTs(timestamp);
            }
            flightLogRepository.save(newFlightLog);
        } else {
            if(!data.getCallsign().isEmpty() && flightLog.get().getCallsign().isEmpty()) {
                flightLog.get().setCallsign(data.getCallsign());
            }
            if(data.getLatitude() != 0) {
                flightLog.get().setLastLatitude(data.getLatitude());
                if(flightLog.get().getFirstLatitude() == 0) {
                    flightLog.get().setFirstLatitude(data.getLatitude());
                }
            }
            if(data.getLongitude() != 0) {
                flightLog.get().setLastLongitude(data.getLongitude());
                if(flightLog.get().getFirstLongitude() == 0) {
                    flightLog.get().setFirstLongitude(data.getLongitude());
                }
            }
            if(data.getAltitude() != 0) {
                flightLog.get().setLastAltitude(data.getAltitude());
                if(flightLog.get().getFirstAltitude() == 0) {
                    flightLog.get().setFirstAltitude(data.getAltitude());
                }
            }
            if(data.getDateMessageGenerated() != null && data.getTimeMessageGenerated() != null) {
                flightLog.get().setLastTs(Timestamp.valueOf(data.getDateMessageGenerated().replace("/", "-")
                        + " " + data.getTimeMessageGenerated()));
            }
            flightLogRepository.save(flightLog.get());
        }
    }
}
