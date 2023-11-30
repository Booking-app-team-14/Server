package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class AccommodationDTO {

    private Long id;
    private String name;
    private String description;
    private Long locationId;
    private Long accommodationtypeId;
    private Set<Long> imageId;
    private Set<Long> amenitiyId;
    private Double rating;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<Long> availabilityId;
    private Double pricePerNight;
    private boolean pricePerGuest;
    private Integer cancellationDeadline;
    private Long ownerId;

    public AccommodationDTO(Long id, String name, String description, Long locationId, Long accommodationtypeId,
                            Set<Long> imageId, Set<Long> amenitiyId, Double rating, Integer minNumberOfGuests,
                            Integer maxNumberOfGuests, Set<Long> availabilityId, Double pricePerNight,
                            boolean pricePerGuest, Integer cancellationDeadline, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locationId = locationId;
        this.accommodationtypeId = accommodationtypeId;
        this.imageId = imageId;
        this.amenitiyId = amenitiyId;
        this.rating = rating;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.availabilityId = availabilityId;
        this.pricePerNight = pricePerNight;
        this.pricePerGuest = pricePerGuest;
        this.cancellationDeadline = cancellationDeadline;
        this.ownerId = ownerId;
    }


}

