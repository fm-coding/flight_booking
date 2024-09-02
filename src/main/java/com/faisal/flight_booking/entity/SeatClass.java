package com.faisal.flight_booking.entity;

import jakarta.persistence.*;

@Entity
public class SeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    public SeatClass(long l, String first, Airplane airplane) {
    }

    public SeatClass(String name, Airplane airplane) {
        this.name = name;
        this.airplane = airplane;
    }

    public SeatClass() {

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
