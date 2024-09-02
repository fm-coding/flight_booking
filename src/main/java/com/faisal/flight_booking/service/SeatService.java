package com.faisal.flight_booking.service;

import com.faisal.flight_booking.entity.Seat;
import com.faisal.flight_booking.entity.SeatClass;

import java.util.List;

public interface SeatService {
    List<Seat> initializeSeatClassesAndSeats(Long airplaneId);
    List<SeatClass> getSeatClassesByAirplaneId(Long airplaneId);
    List<Seat> getSeatsByAirplaneId(Long airplaneId);

    void addSeatClassesForAirplane(Long airplaneId);

    List<SeatClass> getSeatClasses(Long airplaneId);
}
