package com.faisal.flight_booking.service;

import com.faisal.flight_booking.entity.Airport;

import java.util.List;

public interface AirportService {

    List<Airport> getAllAirports();

    Airport getAirportById(Long id);

    Airport createAirport(Airport airport);

    Airport updateAirport(Long id, Airport airport);

    boolean deleteAirport(Long id);
}
