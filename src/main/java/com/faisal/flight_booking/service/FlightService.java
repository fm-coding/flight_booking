package com.faisal.flight_booking.service;

import com.faisal.flight_booking.entity.Flight;

import java.util.List;

public interface FlightService {

    Flight createFlight(Flight flight);

    List<Flight> getAllFlights();

    Flight getFlightById(Long id);

    Flight updateFlight(Long id, Flight flight);

    boolean deleteFlight(Long id);

    List<Flight> createFlightsAutomated(Long airplaneId, Long departureAirportId, Long arrivalAirportId, String departureTime, String arrivalTime);

    Flight getFlightByFlightNumber(String flightNumber);
}
