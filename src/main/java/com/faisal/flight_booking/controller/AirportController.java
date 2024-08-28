package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Airport;
import com.faisal.flight_booking.exception.DuplicateAirportException;
import com.faisal.flight_booking.exception.ResourceNotFoundException;
import com.faisal.flight_booking.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createAirport(@Valid @RequestBody Airport airport) {
        try {
            Airport createdAirport = airportService.createAirport(airport);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("Airport created successfully.", createdAirport));
        } catch (DuplicateAirportException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred while creating the airport."));
        }
    }

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getAirportById(@PathVariable Long id) {
        try {
            Airport airport = airportService.getAirportById(id);
            return ResponseEntity.ok(new ResponseMessage("Airport found.", airport));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred while retrieving the airport."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateAirport(@PathVariable Long id, @Valid @RequestBody Airport airport) {
        try {
            Airport updatedAirport = airportService.updateAirport(id, airport);
            return ResponseEntity.ok(new ResponseMessage("Airport updated successfully.", updatedAirport));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        } catch (DuplicateAirportException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred while updating the airport."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteAirport(@PathVariable Long id) {
        try {
            boolean isDeleted = airportService.deleteAirport(id);
            if (isDeleted) {
                return ResponseEntity.ok(new ResponseMessage("Airport deleted successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Airport with ID " + id + " not found for deletion."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("An error occurred while deleting the airport."));
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
}
