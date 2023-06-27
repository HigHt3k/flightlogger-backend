package com.home_app.service;

import com.home_app.model.dump1090.Dump1090Data;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Dump1090DataQueue {

    private final Queue<Dump1090Data> dataQueue;

    public Dump1090DataQueue() {
        this.dataQueue = new ConcurrentLinkedQueue<>();
    }

    public void addData(Dump1090Data dump1090Data) {
        dataQueue.add(dump1090Data);
    }

    public Dump1090Data getNextData() {
        return dataQueue.poll();
    }

    public boolean hasData() {
        return !dataQueue.isEmpty();
    }

    public int currentQueueSize() {
        return dataQueue.size();
    }
}
