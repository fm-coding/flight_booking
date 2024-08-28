package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByName(String name);
}
