package com.faisal.flight_booking.controller;

import com.faisal.flight_booking.entity.Booking;
import com.faisal.flight_booking.entity.Customer;
import com.faisal.flight_booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookSeat(@RequestBody BookingRequest bookingRequest) {
        // Extract the details from the request body
        Long airplaneId = bookingRequest.getAirplaneId();
        String seatNumber = bookingRequest.getSeatNumber();
        Customer customer = bookingRequest.getCustomerDetails();

        // Perform the booking
        Booking booking = bookingService.bookSeat(airplaneId, seatNumber, customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @PostMapping("/pay/{bookingId}")
    public ResponseEntity<Booking> payForBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.handlePayment(bookingId);
        return ResponseEntity.ok(booking);
    }

    // Define the BookingRequest class to help with the request body
    public static class BookingRequest {
        private Long airplaneId;
        private String seatNumber;
        private Customer customerDetails;

        // Getters and setters
        public Long getAirplaneId() {
            return airplaneId;
        }

        public void setAirplaneId(Long airplaneId) {
            this.airplaneId = airplaneId;
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        public Customer getCustomerDetails() {
            return customerDetails;
        }

        public void setCustomerDetails(Customer customerDetails) {
            this.customerDetails = customerDetails;
        }
    }
}
