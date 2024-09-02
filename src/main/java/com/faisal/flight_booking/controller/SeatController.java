package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Seat;
import com.faisal.flight_booking.entity.SeatClass;
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

    @GetMapping("/classes/{airplaneId}")
    public ResponseEntity<List<SeatClass>> getSeatClasses(@PathVariable Long airplaneId) {
        List<SeatClass> seatClasses = seatService.getSeatClassesByAirplaneId(airplaneId);
        return ResponseEntity.ok(seatClasses);
    }

    @GetMapping("/{airplaneId}")
    public ResponseEntity<List<Seat>> getSeats(@PathVariable Long airplaneId) {
        List<Seat> seats = seatService.getSeatsByAirplaneId(airplaneId);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/initialize/{airplaneId}")
    public ResponseEntity<List<Seat>> initializeSeats(@PathVariable Long airplaneId) {
        List<Seat> initializedSeats = seatService.initializeSeatClassesAndSeats(airplaneId);
        return ResponseEntity.status(HttpStatus.CREATED).body(initializedSeats);
    }
}
