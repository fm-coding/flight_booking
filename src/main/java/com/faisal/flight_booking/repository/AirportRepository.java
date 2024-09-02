package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByName(String name);
    List<Airport> findByCity(String city);
}
