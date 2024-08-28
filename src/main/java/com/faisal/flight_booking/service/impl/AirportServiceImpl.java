package com.faisal.flight_booking.service.impl;

import com.faisal.flight_booking.entity.Airport;
import com.faisal.flight_booking.exception.DuplicateAirportException;
import com.faisal.flight_booking.exception.ResourceNotFoundException;
import com.faisal.flight_booking.repository.AirportRepository;
import com.faisal.flight_booking.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getAirportById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport with ID " + id + " not found"));
    }

    @Override
    public Airport createAirport(Airport airport) {
        if (airport.getType() == null) {
            throw new IllegalArgumentException("Airport type cannot be null");
        }

        if (airportRepository.existsByName(airport.getName())) {
            throw new DuplicateAirportException("Airport with name '" + airport.getName() + "' already exists");
        }

        return airportRepository.save(airport);
    }

    @Override
    public Airport updateAirport(Long id, Airport airport) {
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airport with ID " + id + " not found");
        }

        if (airportRepository.existsByName(airport.getName())) {
            throw new DuplicateAirportException("Airport with name '" + airport.getName() + "' already exists");
        }

        airport.setId(id);
        return airportRepository.save(airport);
    }

    @Override
    public boolean deleteAirport(Long id) {
        if (!airportRepository.existsById(id)) {
            return false;
        }
        airportRepository.deleteById(id);
        return true;
    }
}
