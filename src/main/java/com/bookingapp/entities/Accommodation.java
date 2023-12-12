package com.bookingapp.entities;


import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.AmenityDTO;
import com.bookingapp.dtos.AvailabilityDTO;
import com.bookingapp.dtos.LocationDTO;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.AmenityRepository;
import com.bookingapp.services.AmenityService;
import com.bookingapp.services.AvailabilityService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
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

    //@ManyToOne
    //private Owner owner;

    public Accommodation(AccommodationDTO accommodationDTO, AmenityService amenityService, AvailabilityService availabilityService) {
        this.name = accommodationDTO.getName();
        this.description = accommodationDTO.getDescription();
        Location location = new Location();
        location.setId(accommodationDTO.getLocation().getId());
        this.location = location;
        this.type = accommodationDTO.getType();

        Set<Long> amenityIds = accommodationDTO.getAmenities().stream()
                .map(AmenityDTO::getId)
                .collect(Collectors.toSet());

        // Fetch Amenities from the database using AmenityRepository
        this.amenities= new HashSet<>();
        this.amenities.addAll(amenityService.findAllById(amenityIds));

        Set<Long> availabilityIds = accommodationDTO.getAvailability().stream()
                .map(AvailabilityDTO::getId)
                .collect(Collectors.toSet());

        this.availability = new HashSet<>();
        this.availability.addAll(availabilityService.findAllById(amenityIds));

        this.images = accommodationDTO.getImages();
        this.rating = accommodationDTO.getRating();
        this.minNumberOfGuests = accommodationDTO.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodationDTO.getMaxNumberOfGuests();
        this.pricePerGuest = accommodationDTO.isPricePerGuest();
        this.pricePerNight = accommodationDTO.getPricePerNight();
        this.cancellationDeadline = accommodationDTO.getCancellationDeadline();
    }

    public Accommodation() {

    }
}