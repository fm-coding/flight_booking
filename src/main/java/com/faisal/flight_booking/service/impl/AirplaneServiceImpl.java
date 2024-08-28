package com.faisal.flight_booking.service.impl;

import com.faisal.flight_booking.entity.Airplane;
import com.faisal.flight_booking.exception.ResourceNotFoundException;
import com.faisal.flight_booking.repository.AirplaneRepository;
import com.faisal.flight_booking.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Override
    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane getAirplaneById(Long id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airplane with ID " + id + " not found"));
    }

    @Override
    public Airplane createAirplane(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    @Override
    public Airplane updateAirplane(Long id, Airplane airplane) {
        if (!airplaneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airplane with ID " + id + " not found");
        }

        airplane.setId(id);
        return airplaneRepository.save(airplane);
    }

    @Override
    public boolean deleteAirplane(Long id) {
        if (!airplaneRepository.existsById(id)) {
            return false;
        }
        airplaneRepository.deleteById(id);
        return true;
    }
}
