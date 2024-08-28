package com.faisal.flight_booking.service;

import com.faisal.flight_booking.entity.Airplane;

import java.util.List;

public interface AirplaneService {
    List<Airplane> getAllAirplanes();

    Airplane getAirplaneById(Long id);

    Airplane createAirplane(Airplane airplane);

    Airplane updateAirplane(Long id, Airplane airplane);

    boolean deleteAirplane(Long id);
}
