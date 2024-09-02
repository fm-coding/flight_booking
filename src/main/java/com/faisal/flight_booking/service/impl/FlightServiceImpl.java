package com.faisal.flight_booking.service.impl;

import com.faisal.flight_booking.entity.Flight;
import com.faisal.flight_booking.entity.Airplane;
import com.faisal.flight_booking.entity.Airport;
import com.faisal.flight_booking.repository.FlightRepository;
import com.faisal.flight_booking.repository.AirplaneRepository;
import com.faisal.flight_booking.repository.AirportRepository;
import com.faisal.flight_booking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id " + id));
    }

    @Override
    public Flight updateFlight(Long id, Flight flight) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found with id " + id);
        }
        flight.setId(id);
        return flightRepository.save(flight);
    }

    @Override
    public boolean deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found with id " + id);
        }
        flightRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Flight> createFlightsAutomated(Long airplaneId, Long departureAirportId, Long arrivalAirportId, String departureTime, String arrivalTime) {
        Airplane airplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new RuntimeException("Airplane not found with id " + airplaneId));
        Airport departureAirport = airportRepository.findById(departureAirportId)
                .orElseThrow(() -> new RuntimeException("Departure airport not found with id " + departureAirportId));
        Airport arrivalAirport = airportRepository.findById(arrivalAirportId)
                .orElseThrow(() -> new RuntimeException("Arrival airport not found with id " + arrivalAirportId));

        // Ensure the airplane is at the correct departure airport
        if (!airplane.getAirport().equals(departureAirport)) {
            throw new RuntimeException("Airplane with ID " + airplaneId + " is not situated at the departure airport with ID " + departureAirportId);
        }

        // Generate a unique flight number based on departure and arrival information
        String uniqueFlightNumber = generateUniqueFlightNumber(departureAirport, arrivalAirport, departureTime);

        // Check if a flight with the generated number already exists
        boolean exists = flightRepository.existsByFlightNumberAndDepartureTimeAndDepartureAirport_Id(
                uniqueFlightNumber, departureTime, departureAirportId);

        if (exists) {
            throw new RuntimeException("Flight with number " + uniqueFlightNumber + " already exists for the given departure time and airport.");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(uniqueFlightNumber);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setAirplane(airplane);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);

        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        return flightRepository.saveAll(flights);
    }

    private String generateUniqueFlightNumber(Airport departureAirport, Airport arrivalAirport, String departureTime) {
        return "FL" + departureAirport.getCode() + arrivalAirport.getCode() + departureTime.replaceAll("[-:T]", "");
    }


    @Override
    public Flight getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found with flight number " + flightNumber));
    }
}
