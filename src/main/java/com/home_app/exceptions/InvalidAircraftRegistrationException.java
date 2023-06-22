package com.home_app.exceptions;

public class InvalidAircraftRegistrationException extends IllegalArgumentException {

    public InvalidAircraftRegistrationException(String message) {
        super(message);
    }
}
