package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by customer ID
    List<Booking> findByCustomerId(Long customerId);

    // Count bookings by airplane ID
    long countByFlight_Airplane_Id(Long airplaneId);

    boolean existsByFlight_Airplane_IdAndCustomer_PassportNumber(Long airplaneId, String passportNumber);

}
