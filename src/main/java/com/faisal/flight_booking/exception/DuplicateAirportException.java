package com.faisal.flight_booking.exception;

public class DuplicateAirportException extends RuntimeException {
    public DuplicateAirportException(String message) {
        super(message);
    }
}

