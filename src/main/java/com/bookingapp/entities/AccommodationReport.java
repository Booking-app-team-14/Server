package com.bookingapp.entities;

import com.bookingapp.enums.AccommodationType;
import jakarta.persistence.*;
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

    @Column(nullable=false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    private Location location;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @Column(nullable=false)
    private String image;

    @Column(nullable=false)
    private Double rating;

    @Column(nullable=false)
    private Integer minNumberOfGuests;

    @Column(nullable=false)
    private Integer maxNumberOfGuests;


    @Column(nullable=false)
    private Double pricePerNight;

    @ManyToOne
    private Owner owner;

    @Column(nullable = false)
    private int numberOfReservations;

    @Column(nullable = false)
    private double totalProfit;

}
