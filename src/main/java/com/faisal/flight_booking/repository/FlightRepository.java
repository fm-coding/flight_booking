package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Flight;
import com.faisal.flight_booking.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumberAndDepartureTimeAndDepartureAirport_Id(String flightNumber, String departureTime, Long departureAirportId);
}
