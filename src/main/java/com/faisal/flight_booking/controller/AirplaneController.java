package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Airplane;
import com.faisal.flight_booking.exception.ResourceNotFoundException;
import com.faisal.flight_booking.service.AirplaneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airplanes")
public class AirplaneController {

    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createAirplane(@Valid @RequestBody Airplane airplane) {
        Airplane createdAirplane = airplaneService.createAirplane(airplane);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessage("Airplane created successfully.", createdAirplane));
    }

    @GetMapping
    public ResponseEntity<List<Airplane>> getAllAirplanes() {
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        return ResponseEntity.ok(airplanes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getAirplaneById(@PathVariable Long id) {
        try {
            Airplane airplane = airplaneService.getAirplaneById(id);
            return ResponseEntity.ok(new ResponseMessage("Airplane found.", airplane));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateAirplane(@PathVariable Long id, @Valid @RequestBody Airplane airplane) {
        try {
            Airplane updatedAirplane = airplaneService.updateAirplane(id, airplane);
            return ResponseEntity.ok(new ResponseMessage("Airplane updated successfully.", updatedAirplane));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteAirplane(@PathVariable Long id) {
        boolean isDeleted = airplaneService.deleteAirplane(id);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseMessage("Airplane deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Airplane with ID " + id + " not found for deletion."));
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
