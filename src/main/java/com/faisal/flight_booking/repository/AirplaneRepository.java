package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    boolean existsByModel(String model);
}
