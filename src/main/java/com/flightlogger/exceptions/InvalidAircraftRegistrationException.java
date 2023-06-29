package com.flightlogger.exceptions;

public class InvalidAircraftRegistrationException extends IllegalArgumentException {

    public InvalidAircraftRegistrationException(String message) {
        super(message);
    }
}
