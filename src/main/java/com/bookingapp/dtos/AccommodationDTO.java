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
    private Long locationId;
    private AccommodationType type;
    private Set<String> images;
    private Set<Long> amenityId;
    private Double rating;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<Long> availabilityId;
    private Double pricePerNight;
    private boolean pricePerGuest;
    private Integer cancellationDeadline;
    //private Long ownerId;

    public AccommodationDTO(Long id, String name, String description, Long locationId, AccommodationType type,
                            Set<String> images, Set<Long> amenityId, Double rating, Integer minNumberOfGuests,
                            Integer maxNumberOfGuests, Set<Long> availabilityId, Double pricePerNight,
                            boolean pricePerGuest, Integer cancellationDeadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locationId = locationId;
        this.type=type;
        this.images = images;
        this.amenityId = amenityId;
        this.rating = rating;
        this.minNumberOfGuests = minNumberOfGuests;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.availabilityId = availabilityId;
        this.pricePerNight = pricePerNight;
        this.pricePerGuest = pricePerGuest;
        this.cancellationDeadline = cancellationDeadline;
        //this.ownerId = ownerId;
    }

    public AccommodationDTO() {
    }



    public AccommodationDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.locationId = accommodation.getLocation().getId();
        this.type = accommodation.getType();
        this.images = accommodation.getImages();
        //this.amenityId = accommodation.getAmenities().
        this.amenityId = accommodation.getAmenities().stream().map(Amenity::getId).collect(Collectors.toSet());
        this.rating = accommodation.getRating();
        this.minNumberOfGuests = accommodation.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        //this.availabilityId = getAvailabilityId();
        this.availabilityId = accommodation.getAvailability().stream().map(Availability::getId).collect(Collectors.toSet());
        this.pricePerNight = accommodation.getPricePerNight();
        this.pricePerGuest = accommodation.isPricePerGuest();
        this.cancellationDeadline = accommodation.getCancellationDeadline();
        //this.ownerId = accommodation.getOwner().getId();
    }

}

