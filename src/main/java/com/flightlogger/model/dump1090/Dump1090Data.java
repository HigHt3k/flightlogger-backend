package com.flightlogger.model.dump1090;

import jakarta.persistence.*;

@Entity
@Table(name =  "message_log")
public class Dump1090Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_log_id")
    private Integer messageLogId;

    private String messageType;
    private int transmissionType;
    private int sessionId;
    @Column(name = "aircraft_id")
    private int icao24Decimal;
    private String hexIdent;
    private String flightNumber;
    private String dateMessageGenerated;
    private String timeMessageGenerated;
    private String dateMessageLogged;
    private String timeMessageLogged;
    private String callsign;
    private int altitude;
    private int groundSpeed;
    private String track;
    private double latitude;
    private double longitude;
    private int verticalRate;
    private String squawk;
    private boolean alertFlag;
    private boolean emergencyFlag;
    private boolean spiFlag;
    private boolean groundFlag;
    private String rawMessage;

    // Constructors, getters, and setters

    public Dump1090Data() {
    }

    public Dump1090Data(String messageType, int transmissionType, int sessionId, int icao24Decimal, String hexIdent, String flightNumber, String dateMessageGenerated, String timeMessageGenerated,
                        String dateMessageLogged, String timeMessageLogged, String callsign,
                        int altitude, int groundSpeed, String track, double latitude, double longitude,
                        int verticalRate, String squawk, boolean alertFlag, boolean emergencyFlag, boolean spiFlag, boolean groundFlag, String rawMessage) {
        this.messageType = messageType;
        this.transmissionType = transmissionType;
        this.sessionId = sessionId;
        this.icao24Decimal = icao24Decimal;
        this.hexIdent = hexIdent;
        this.flightNumber = flightNumber;
        this.dateMessageGenerated = dateMessageGenerated;
        this.timeMessageGenerated = timeMessageGenerated;
        this.dateMessageLogged = dateMessageLogged;
        this.timeMessageLogged = timeMessageLogged;
        this.callsign = callsign;
        this.altitude = altitude;
        this.groundSpeed = groundSpeed;
        this.track = track;
        this.latitude = latitude;
        this.longitude = longitude;
        this.verticalRate = verticalRate;
        this.squawk = squawk;
        this.alertFlag = alertFlag;
        this.emergencyFlag = emergencyFlag;
        this.spiFlag = spiFlag;
        this.groundFlag = groundFlag;
        this.rawMessage = rawMessage;
    }

    // Getters and setters for all variables

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(int transmissionType) {
        this.transmissionType = transmissionType;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getIcao24Decimal() {
        return icao24Decimal;
    }

    public void setIcao24Decimal(int icao24Decimal) {
        this.icao24Decimal = icao24Decimal;
    }

    public String getHexIdent() {
        return hexIdent;
    }

    public void setHexIdent(String hexIdent) {
        this.hexIdent = hexIdent;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDateMessageGenerated() {
        return dateMessageGenerated;
    }

    public void setDateMessageGenerated(String dateMessageGenerated) {
        this.dateMessageGenerated = dateMessageGenerated;
    }

    public String getTimeMessageGenerated() {
        return timeMessageGenerated;
    }

    public void setTimeMessageGenerated(String timeMessageGenerated) {
        this.timeMessageGenerated = timeMessageGenerated;
    }

    public String getDateMessageLogged() {
        return dateMessageLogged;
    }

    public void setDateMessageLogged(String dateMessageLogged) {
        this.dateMessageLogged = dateMessageLogged;
    }

    public String getTimeMessageLogged() {
        return timeMessageLogged;
    }

    public void setTimeMessageLogged(String timeMessageLogged) {
        this.timeMessageLogged = timeMessageLogged;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getGroundSpeed() {
        return groundSpeed;
    }

    public void setGroundSpeed(int groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVerticalRate() {
        return verticalRate;
    }

    public void setVerticalRate(int verticalRate) {
        this.verticalRate = verticalRate;
    }

    public String getSquawk() {
        return squawk;
    }

    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    public boolean getAlertFlag() {
        return alertFlag;
    }

    public void setAlertFlag(boolean alertFlag) {
        this.alertFlag = alertFlag;
    }

    public boolean getEmergencyFlag() {
        return emergencyFlag;
    }

    public void setEmergencyFlag(boolean emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }

    public boolean getSpiFlag() {
        return spiFlag;
    }

    public void setSpiFlag(boolean spiFlag) {
        this.spiFlag = spiFlag;
    }

    public boolean getGroundFlag() {
        return groundFlag;
    }

    public void setGroundFlag(boolean groundFlag) {
        this.groundFlag = groundFlag;
    }

    public Integer getMessageLogId() {
        return messageLogId;
    }

    public void setMessageLogId(Integer messageLogId) {
        this.messageLogId = messageLogId;
    }

    public boolean isAlertFlag() {
        return alertFlag;
    }

    public boolean isEmergencyFlag() {
        return emergencyFlag;
    }

    public boolean isSpiFlag() {
        return spiFlag;
    }

    public boolean isGroundFlag() {
        return groundFlag;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }
}
