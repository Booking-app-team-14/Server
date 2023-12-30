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

    @ElementCollection
    @Column(nullable=false)
    private Set<String> images;

    /*@ManyToMany (cascade = CascadeType.ALL)
    private Set<Amenity> amenities;*/

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accmmodation_amenities",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
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

    @Column(nullable=false)
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserAccount owner;


    public Accommodation(AccommodationDTO accommodationDTO) {
        this.name = accommodationDTO.getName();
        this.description = accommodationDTO.getDescription();
        this.type = accommodationDTO.getType();
        this.images = accommodationDTO.getImages();
        this.rating = accommodationDTO.getRating();
        this.minNumberOfGuests = accommodationDTO.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodationDTO.getMaxNumberOfGuests();
        this.pricePerGuest = accommodationDTO.isPricePerGuest();
        this.pricePerNight = accommodationDTO.getPricePerNight();
        this.cancellationDeadline = accommodationDTO.getCancellationDeadline();
        this.approved = false;
    }

    public Accommodation() {
        this.amenities= new HashSet<>();
        this.availability = new HashSet<Availability>();

    }


}