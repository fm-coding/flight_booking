package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Seat;
import com.faisal.flight_booking.exception.AirplaneNotFoundException;
import com.faisal.flight_booking.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<Seat>> getSeatsByFlightId(@PathVariable Long flightId) {
        try {
            List<Seat> seats = seatService.getSeatsByFlightId(flightId);
            return ResponseEntity.ok(seats);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/initialize/{airplaneId}")
    public ResponseEntity<?> initializeSeats(@PathVariable Long airplaneId) {
        try {
            List<Seat> initializedSeats = seatService.initializeSeatClassesAndSeats(airplaneId);
            return ResponseEntity.status(HttpStatus.CREATED).body(initializedSeats);
        } catch (AirplaneNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
