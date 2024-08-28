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
@Table(name = "Airport")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Code cannot be blank")
    @Column(nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "City cannot be blank")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Country cannot be blank")
    @Column(nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Airport type cannot be null")
    @Column(nullable = false)
    private AirportType type;

    public enum AirportType {
        Domestic,
        International
    }
}
