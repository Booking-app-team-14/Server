package com.bookingapp.entities;


import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
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

    //@ManyToOne
    //private Owner owner;

    public Accommodation(AccommodationDTO accommodationDTO) {
        this.name=accommodationDTO.getName();
        this.description=accommodationDTO.getDescription();
        Location location = new Location(null);  //????
        location.setId(accommodationDTO.getLocationId());
        this.location = location;
        this.type=accommodationDTO.getType();
        this.images = accommodationDTO.getImages();

        //this.amenities = accommodationDTO.setAmenityId();
        // kreiranje nove instance Amenity koristeci amenityId iz DTO-a
        Set<Amenity> amenitySet = Collections.emptySet();

        if (accommodationDTO.getAmenityId() != null) {
            amenitySet = accommodationDTO.getAmenityId().stream()
                    .filter(Objects::nonNull)  // Filtriranje null vrednosti
                    .map(amenityId -> {
                        Amenity amenity = new Amenity(null);  //??
                        amenity.setId(amenityId);
                        return amenity;
                    })
                    .collect(Collectors.toSet());
        }

        this.amenities = amenitySet;

        this.rating=0.0;
        this.minNumberOfGuests = accommodationDTO.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodationDTO.getMaxNumberOfGuests();
        //this.availability = accommodationDTO.getAvailabilityId();
        // kreiranje nove instance availability koristeci availabilityId iz DTO-a
        Set<Availability> availabilitySet = Collections.emptySet();

        if (accommodationDTO.getAvailabilityId() != null) {
            availabilitySet = accommodationDTO.getAvailabilityId().stream()
                    .filter(Objects::nonNull)  // Filtriranje null vrednosti
                    .map(availabilityId -> {
                        Availability availability = new Availability(null); //??
                        availability.setId(availabilityId);
                        return availability;
                    })
                    .collect(Collectors.toSet());
        }


        this.availability = availabilitySet;
        this.pricePerGuest = accommodationDTO.isPricePerGuest();
        this.pricePerNight = accommodationDTO.getPricePerNight();
        this.cancellationDeadline = accommodationDTO.getCancellationDeadline();
    }

    //@Column(nullable=false)
    //private RequestHandling reservationRequestHandling;
}
