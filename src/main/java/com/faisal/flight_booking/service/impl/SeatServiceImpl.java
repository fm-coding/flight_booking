package com.faisal.flight_booking.service.impl;

import com.faisal.flight_booking.entity.Seat;
import com.faisal.flight_booking.entity.SeatClass;
import com.faisal.flight_booking.entity.Airplane;
import com.faisal.flight_booking.exception.AirplaneNotFoundException;
import com.faisal.flight_booking.repository.SeatClassRepository;
import com.faisal.flight_booking.repository.SeatRepository;
import com.faisal.flight_booking.repository.AirplaneRepository;
import com.faisal.flight_booking.service.SeatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatClassRepository seatClassRepository;
    private final AirplaneRepository airplaneRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository, SeatClassRepository seatClassRepository, AirplaneRepository airplaneRepository) {
        this.seatRepository = seatRepository;
        this.seatClassRepository = seatClassRepository;
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    @Transactional
    public List<Seat> initializeSeatClassesAndSeats(Long airplaneId) {
        Airplane airplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new AirplaneNotFoundException("Airplane not found with id " + airplaneId));

        // Check if seats have already been initialized for this airplane
        if (!seatRepository.findByAirplaneId(airplaneId).isEmpty()) {
            throw new RuntimeException("Seats have already been initialized for this airplane.");
        }

        // Check if seat classes already exist
        SeatClass firstClass = seatClassRepository.findById(1L).orElse(new SeatClass(1L, "First", airplane));
        SeatClass businessClass = seatClassRepository.findById(2L).orElse(new SeatClass(2L, "Business", airplane));
        SeatClass economyClass = seatClassRepository.findById(3L).orElse(new SeatClass(3L, "Economy", airplane));

        seatClassRepository.save(firstClass);
        seatClassRepository.save(businessClass);
        seatClassRepository.save(economyClass);

        int totalSeats = airplane.getCapacity();
        int firstClassSeats = totalSeats / 8;
        int businessClassSeats = (totalSeats * 2) / 8;
        int economyClassSeats = totalSeats - (firstClassSeats + businessClassSeats);

        List<Seat> seats = new ArrayList<>();
        int seatNumber = 1;

        // Assign seats to First Class
        for (int i = 0; i < firstClassSeats; i++) {
            seats.add(new Seat("F" + seatNumber++, firstClass, airplane));
        }

        // Assign seats to Business Class
        for (int i = 0; i < businessClassSeats; i++) {
            seats.add(new Seat("B" + seatNumber++, businessClass, airplane));
        }

        // Assign seats to Economy Class
        for (int i = 0; i < economyClassSeats; i++) {
            seats.add(new Seat("E" + seatNumber++, economyClass, airplane));
        }

        seatRepository.saveAll(seats);
        return seats;
    }

    @Override
    public List<SeatClass> getSeatClassesByAirplaneId(Long airplaneId) {
        return seatClassRepository.findByAirplaneId(airplaneId);
    }

    @Override
    public List<Seat> getSeatsByAirplaneId(Long airplaneId) {
        return seatRepository.findByAirplaneId(airplaneId);
    }

    @Override
    public void addSeatClassesForAirplane(Long airplaneId) {
        initializeSeatClassesAndSeats(airplaneId); // Use the method to initialize seats and classes
    }

    @Override
    public List<SeatClass> getSeatClasses(Long airplaneId) {
        return seatClassRepository.findByAirplaneId(airplaneId);
    }
}
