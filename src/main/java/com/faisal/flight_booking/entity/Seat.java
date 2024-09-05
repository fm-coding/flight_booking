package com.faisal.flight_booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    private boolean isTaken;

    @ManyToOne
    @JoinColumn(name = "seat_class_id")
    private SeatClass seatClass;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public Seat() {}

    public Seat(String seatNumber, SeatClass seatClass, Airplane airplane) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.airplane = airplane;
    }
}
