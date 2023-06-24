package com.home_app.controller.task;

import com.home_app.model.dump1090.Dump1090Data;
import com.home_app.model.dump1090.MessageLogRepository;
import com.home_app.service.Dump1090DataQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SaveDump1090DataToDatabaseTask {

    private final Logger logger = LoggerFactory.getLogger(SaveDump1090DataToDatabaseTask.class);

    private Dump1090DataQueue dataQueue;

    private MessageLogRepository messageLogRepository;

    @Autowired
    public SaveDump1090DataToDatabaseTask(Dump1090DataQueue dataQueue, MessageLogRepository messageLogRepository) {
        this.dataQueue = dataQueue;
        this.messageLogRepository = messageLogRepository;
    }

    @Scheduled(fixedRate = 1000)
    public void saveDataToDatabase() {
        int currentQueueSize = dataQueue.currentQueueSize();
        logger.info("Writing a total of {} messages to the database.", currentQueueSize);
        for(int i = 0; i < currentQueueSize; i++) {
            Dump1090Data data = dataQueue.getNextData();
            messageLogRepository.save(data);
        }
    }
}
