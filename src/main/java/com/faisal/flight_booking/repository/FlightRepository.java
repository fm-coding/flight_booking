package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumberAndDepartureTimeAndDepartureAirport_Id(String flightNumber, String departureTime, Long departureAirportId);

    @Query("SELECT f FROM Flight f WHERE f.departureAirport.city = :departureCity AND f.arrivalAirport.city = :arrivalCity")
    List<Flight> findFlightsByDepartureCityAndArrivalCity(@Param("departureCity") String departureCity,
                                                          @Param("arrivalCity") String arrivalCity);

    Optional<Flight> findByAirplane_Id(Long airplaneId);
}
