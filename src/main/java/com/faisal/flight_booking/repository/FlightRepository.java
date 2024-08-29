package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findTopByOrderByFlightNumberDesc();
}
