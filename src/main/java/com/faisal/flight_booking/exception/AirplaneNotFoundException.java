package com.faisal.flight_booking.exception;

public class AirplaneNotFoundException extends RuntimeException {
    public AirplaneNotFoundException(String message) {
        super(message);
    }
}
