package com.bookingapp.entities;

import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.AmenityDTO;
import com.bookingapp.dtos.AvailabilityDTO;
import com.bookingapp.dtos.LocationDTO;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.enums.Handling;
import com.bookingapp.repositories.AmenityRepository;
import com.bookingapp.services.AmenityService;
import com.bookingapp.services.AvailabilityService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 100)
    @Column(nullable=false)
    private String name;

    @NotEmpty
    @Column(nullable=false)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @ElementCollection
    @Column(nullable=false)
    private Set<String> images;

    @NotNull
    @ManyToMany()
    @JoinTable(
            name = "accmmodation_amenities",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities;

    @Min(value = -1)
    @Column(nullable=false)
    private Double rating;

    @Min(value = 1)
    @Column(nullable=false)
    private Integer minNumberOfGuests;

    @Min(value = 1)
    @Column(nullable=false)
    private Integer maxNumberOfGuests;

    @NotNull
    @OneToMany (cascade = CascadeType.ALL)
    private Set<Availability> availability;

    @Min(value = 1)
    @Column(nullable=false)
    private Double pricePerNight;

    @Column(nullable=false)
    private boolean pricePerGuest;

    @Column(nullable = false)
    private boolean automatic;

    @Min(value = 0)
    @Column(nullable=false)
    private Integer cancellationDeadline;

    @Column(nullable=false)
    private boolean approved;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserAccount owner;

    public Accommodation() {
        this.amenities= new HashSet<>();
        this.availability = new HashSet<Availability>();

    }

    public boolean getPricePerGuest() {
        return this.pricePerGuest;
    }

}