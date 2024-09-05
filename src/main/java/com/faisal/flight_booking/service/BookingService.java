package com.faisal.flight_booking.service;

import com.faisal.flight_booking.entity.Booking;
import com.faisal.flight_booking.entity.Customer;

public interface BookingService {

    Booking bookSeat(Long airplaneId, String seatNumber, Customer customerDetails);

    Booking handlePayment(Long bookingId);
}
