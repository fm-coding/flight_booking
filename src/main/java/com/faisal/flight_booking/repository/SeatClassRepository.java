package com.faisal.flight_booking.repository;

import com.faisal.flight_booking.entity.SeatClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatClassRepository extends JpaRepository<SeatClass, Long> {
    List<SeatClass> findByAirplaneId(Long airplaneId);
}
