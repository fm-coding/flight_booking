package com.faisal.flight_booking.service.impl;

import com.faisal.flight_booking.entity.Booking;
import com.faisal.flight_booking.entity.Customer;
import com.faisal.flight_booking.entity.Flight;
import com.faisal.flight_booking.entity.Seat;
import com.faisal.flight_booking.exception.FlightNotFoundException;
import com.faisal.flight_booking.exception.SeatAlreadyTakenException;
import com.faisal.flight_booking.exception.SeatNotFoundException;
import com.faisal.flight_booking.exception.CapacityExceededException;
import com.faisal.flight_booking.repository.BookingRepository;
import com.faisal.flight_booking.repository.CustomerRepository;
import com.faisal.flight_booking.repository.FlightRepository;
import com.faisal.flight_booking.repository.SeatRepository;
import com.faisal.flight_booking.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository, SeatRepository seatRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Booking bookSeat(Long airplaneId, String seatNumber, Customer customerDetails) {
        // Find the seat by airplane ID and seat number
        Seat seat = seatRepository.findByAirplane_IdAndSeatNumber(airplaneId, seatNumber)
                .orElseThrow(() -> new SeatNotFoundException("Seat not found with seat number: " + seatNumber + " for airplane ID: " + airplaneId));

        // Ensure the seat's airplane matches the requested airplaneId
        if (!seat.getAirplane().getId().equals(airplaneId)) {
            throw new RuntimeException("Seat does not belong to the specified airplane.");
        }

        // Check if the seat is already taken
        if (seat.isTaken()) {
            throw new SeatAlreadyTakenException("Seat with seat number: " + seatNumber + " is already taken.");
        }

        // Check if the customer has already booked a seat on this airplane
        boolean existingBooking = bookingRepository.existsByFlight_Airplane_IdAndCustomer_PassportNumber(
                airplaneId, customerDetails.getPassportNumber());

        if (existingBooking) {
            throw new RuntimeException("Customer with this passport number has already booked a seat on this airplane.");
        }

        // Find the flight associated with the airplane
        Flight flight = flightRepository.findByAirplane_Id(airplaneId)
                .orElseThrow(() -> new FlightNotFoundException("No flight found for the specified airplane."));

        // Get the total capacity of the airplane
        int airplaneCapacity = seat.getAirplane().getCapacity();

        // Get the current number of booked seats for the airplane
        long bookedSeatsCount = bookingRepository.countByFlight_Airplane_Id(airplaneId);

        // Check if booking exceeds the airplane's capacity
        if (bookedSeatsCount >= airplaneCapacity) {
            throw new CapacityExceededException("Cannot book more seats. The airplane's capacity has been reached.");
        }

        // Save customer details
        Customer customer = customerRepository.save(customerDetails);

        // Create the booking
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setSeat(seat);
        booking.setCustomer(customer);
        booking.setSeatNumber(seat.getSeatNumber()); // Ensure seat number is set
        booking.setPaid(false);
        booking.setSeatTaken(true); // Mark the seat as taken when booked

        // Mark the seat as taken
        seat.setTaken(true);
        seatRepository.save(seat);

        // Save the booking
        return bookingRepository.save(booking);
    }

    @Override
    public Booking handlePayment(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        booking.setPaid(true);
        return bookingRepository.save(booking);
    }
}
