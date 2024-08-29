package com.faisal.flight_booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airplane")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Model cannot be blank")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "Manufacturer cannot be blank")
    @Column(nullable = false)
    private String manufacturer;

    @Getter
    @NotNull(message = "Capacity cannot be null")
    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false)
    @NotNull(message = "Airport cannot be null")
    private Airport airport;

    // Getter method for seat count
    public Integer getSeatCount() {
        return capacity;
    }
}
