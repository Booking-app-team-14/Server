package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AccommodationUpdateDTO {

    private Long id;
    private Set<Image> images;
    private String name;
    private String description;
    private String type;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<Long> amenities;
    private LocationDTO location;
    private boolean pricePerGuest;
    private Double defaultPrice;
    private Set<UpdateAvailabilityDTO> availability;
    private Integer cancellationDeadline;
    private String message;

    public AccommodationUpdateDTO(Set<Image> images, String name, String description, String type, Integer minNumberOfGuests, Integer maxNumberOfGuests, Set<Long> amenities, LocationDTO location, boolean pricePerGuest, Double defaultPrice, Set<UpdateAvailabilityDTO> availability, Integer cancellationDeadline, String message) {
        this.images = images;
        this.name = name;
        this.description = description;
        this.type = type;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.amenities = amenities;
        this.location = location;
        this.pricePerGuest = pricePerGuest;
        this.defaultPrice = defaultPrice;
        this.availability = availability;
        this.cancellationDeadline = cancellationDeadline;
        this.message = message;
    }

    public AccommodationUpdateDTO() {
    }

    public boolean getPricePerGuest() {
        return this.pricePerGuest;
    }

}
