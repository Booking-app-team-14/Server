package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccommodationDTO {

    private Long id;
    private String name;
    private String description;
    private LocationDTO location;
    private AccommodationType type;
    private Set<String> images;
    private Set<AmenityDTO> amenities;
    private Double rating;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<AvailabilityDTO> availability;
    private Double pricePerNight;
    private boolean pricePerGuest;
    private Integer cancellationDeadline;

    public AccommodationDTO(
            Long id, String name, String description, LocationDTO location,
            AccommodationType type, Set<String> images, Set<AmenityDTO> amenities,
            Double rating, Integer minNumberOfGuests, Integer maxNumberOfGuests,
            Set<AvailabilityDTO> availability, Double pricePerNight,
            boolean pricePerGuest, Integer cancellationDeadline
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.images = images;
        this.amenities = amenities;
        this.rating = rating;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.availability = availability;
        this.pricePerNight = pricePerNight;
        this.pricePerGuest = pricePerGuest;
        this.cancellationDeadline = cancellationDeadline;
    }

    public AccommodationDTO() {
    }



    public AccommodationDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = new LocationDTO(accommodation.getLocation());
        this.type = accommodation.getType();
        this.images = accommodation.getImages();
        this.amenities = accommodation.getAmenities().stream()
                .map(amenity -> new AmenityDTO(amenity.getId(), amenity.getName(), amenity.getDescription(), amenity.getIcon()))
                .collect(Collectors.toSet());
        this.rating = accommodation.getRating();
        this.minNumberOfGuests = accommodation.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.availability = accommodation.getAvailability().stream()
                .map(availability -> new AvailabilityDTO(availability.getId(), availability.getStartDate(), availability.getEndDate(), availability.getSpecialPrice()))
                .collect(Collectors.toSet());
        this.pricePerNight = accommodation.getPricePerNight();
        this.pricePerGuest = accommodation.isPricePerGuest();
        this.cancellationDeadline = accommodation.getCancellationDeadline();
    }
}

