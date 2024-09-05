package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPassportNumber(String passportNumber);
}
