package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Flight;
import com.faisal.flight_booking.service.FlightService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createFlight(@Valid @RequestBody Flight flight) {
        try {
            Flight createdFlight = flightService.createFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Flight created successfully.", createdFlight));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Error creating flight: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        if (flight != null) {
            return ResponseEntity.ok(new ResponseMessage("Flight found.", flight));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Flight with ID " + id + " not found."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateFlight(@PathVariable Long id, @Valid @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(id, flight);
        if (updatedFlight != null) {
            return ResponseEntity.ok(new ResponseMessage("Flight updated successfully.", updatedFlight));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Flight with ID " + id + " not found for update."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteFlight(@PathVariable Long id) {
        boolean isDeleted = flightService.deleteFlight(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseMessage("Flight deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Flight with ID " + id + " not found for deletion."));
        }
    }

    @PostMapping("/automate")
    public ResponseEntity<ResponseMessage> createFlightsAutomated(@Valid @RequestBody AutomatedFlightRequest request) {
        try {
            List<Flight> createdFlights = flightService.createFlightsAutomated(
                    request.getAirplaneId(),
                    request.getDepartureAirportId(),
                    request.getArrivalAirportId(),
                    request.getDepartureTime(),
                    request.getArrivalTime()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Flights created successfully.", createdFlights));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error creating flights: " + e.getMessage()));
        }
    }

    @GetMapping("/flightNumber/{flightNumber}")
    public ResponseEntity<ResponseMessage> getFlightByFlightNumber(@PathVariable String flightNumber) {
        try {
            Flight flight = flightService.getFlightByFlightNumber(flightNumber);
            return ResponseEntity.ok(new ResponseMessage("Flight found.", flight));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Flight not found with flight number " + flightNumber));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseMessage> searchFlightsByCities(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity) {
        List<Flight> flights = flightService.searchFlightsByCities(departureCity, arrivalCity);
        if (flights.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("No flights found from " + departureCity + " to " + arrivalCity));
        }
        return ResponseEntity.ok(new ResponseMessage("Flights found.", flights));
    }

    @Getter
    public static class ResponseMessage {
        private final String message;
        private Object data;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public ResponseMessage(String message, Object data) {
            this.message = message;
            this.data = data;
        }
    }

    @Setter
    @Getter
    public static class AutomatedFlightRequest {
        private Long airplaneId;
        private Long departureAirportId;
        private Long arrivalAirportId;
        private String departureTime;
        private String arrivalTime;
    }
}
