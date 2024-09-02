package com.faisal.flight_booking.entity;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "seat_class_id")
    private SeatClass seatClass;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    public Seat() {
    }

    public Seat(String seatNumber, SeatClass seatClass, Airplane airplane) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.airplane = airplane;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
