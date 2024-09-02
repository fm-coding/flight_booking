package com.faisal.flight_booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Flight number cannot be blank")
    @Column(nullable = false, unique = true)
    private String flightNumber;

    @NotNull(message = "Departure airport cannot be null")
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @NotNull(message = "Arrival airport cannot be null")
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @NotNull(message = "Airplane cannot be null")
    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @NotBlank(message = "Departure time cannot be blank")
    @Column(nullable = false)
    private String departureTime;

    @NotBlank(message = "Arrival time cannot be blank")
    @Column(nullable = false)
    private String arrivalTime;
}
