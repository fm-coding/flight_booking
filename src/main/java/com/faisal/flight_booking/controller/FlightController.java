package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Flight;
import com.faisal.flight_booking.service.FlightService;
import jakarta.validation.Valid;
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
                    request.getDepartureAirportId(),
                    request.getArrivalAirportId(),
                    request.getAirplaneId(),
                    request.getDepartureTime(),
                    request.getArrivalTime(),
                    request.getPrice()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Flights created successfully.", createdFlights));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error creating flights: " + e.getMessage()));
        }
    }

    // Response message class
    public static class ResponseMessage {
        private String message;
        private Object data;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public ResponseMessage(String message, Object data) {
            this.message = message;
            this.data = data;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    // Request body class for automated flight creation
    public static class AutomatedFlightRequest {
        private Long departureAirportId;
        private Long arrivalAirportId;
        private Long airplaneId;
        private String departureTime;
        private String arrivalTime;
        private Double price;

        // Getters and setters
        public Long getDepartureAirportId() {
            return departureAirportId;
        }

        public void setDepartureAirportId(Long departureAirportId) {
            this.departureAirportId = departureAirportId;
        }

        public Long getArrivalAirportId() {
            return arrivalAirportId;
        }

        public void setArrivalAirportId(Long arrivalAirportId) {
            this.arrivalAirportId = arrivalAirportId;
        }

        public Long getAirplaneId() {
            return airplaneId;
        }

        public void setAirplaneId(Long airplaneId) {
            this.airplaneId = airplaneId;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}
