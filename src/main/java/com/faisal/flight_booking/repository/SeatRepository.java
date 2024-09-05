package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByAirplaneId(Long airplaneId);
    List<Seat> findByFlightId(Long flightId);

    Optional<Seat> findByAirplane_IdAndSeatNumber(Long airplaneId, String seatNumber);
}
