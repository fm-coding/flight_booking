package com.faisal.flight_booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "seat_taken", nullable = false)
    private boolean seatTaken;

    @Column(name = "paid", nullable = false)
    private boolean paid;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    // Getters and Setters

}
