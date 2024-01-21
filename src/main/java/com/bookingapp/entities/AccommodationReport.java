package com.bookingapp.entities;

import com.bookingapp.enums.AccommodationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class AccommodationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 100)
    @Column(nullable=false)
    private String name;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private Location location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @NotEmpty
    @Column(nullable=false)
    private String image;

    @Min(value = -1)
    @Column(nullable=false)
    private Double rating;

    @Min(value = 1)
    @Column(nullable=false)
    private Integer minNumberOfGuests;

    @Min(value = 1)
    @Column(nullable=false)
    private Integer maxNumberOfGuests;

    @Min(value = 1)
    @Column(nullable=false)
    private Double pricePerNight;

    @NotNull
    @ManyToOne
    private Owner owner;

    @Min(value = 0)
    @Column(nullable = false)
    private int numberOfReservations;

    @Min(value = 0)
    @Column(nullable = false)
    private double totalProfit;

}
