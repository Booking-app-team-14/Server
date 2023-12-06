package com.bookingapp.entities;


import com.bookingapp.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
//@Table(name = "accommodations")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    private Location location;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;


    //@Lob
    //@Column(nullable=false)
    //private Set<byte[]> images;

    @ElementCollection
    @Column(nullable=false)
    private Set<String> images;

    @OneToMany (cascade = CascadeType.ALL)
    private Set<Amenity> amenities;

    @Column(nullable=false)
    private Double rating;

    @Column(nullable=false)
    private Integer minNumberOfGuests;

    @Column(nullable=false)
    private Integer maxNumberOfGuests;

    @OneToMany (cascade = CascadeType.ALL)
    private Set<Availability> availability;

    @Column(nullable=false)
    private Double pricePerNight;

    @Column(nullable=false)
    private boolean pricePerGuest;

    @Column(nullable=false)
    private Integer cancellationDeadline;

    @ManyToOne(cascade = CascadeType.ALL)
    private Owner owner;

    //@Column(nullable=false)
    //private RequestHandling reservationRequestHandling;
}
