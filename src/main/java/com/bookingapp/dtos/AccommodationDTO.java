package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AmenityService;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccommodationDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    private Long id;
    private String name;
    private String description;
    private LocationDTO location;
    private AccommodationType type;
    private Set<String> images;
    private Set<String> imageTypes;
    private Set<String> imageBytes;
    private Set<AmenityDTO> amenities;
    private Double rating;
    private Integer minNumberOfGuests;
    private Integer maxNumberOfGuests;
    private Set<AvailabilityDTO> availability;
    private Double pricePerNight;
    private boolean pricePerGuest;
    private Integer cancellationDeadline;
    private Long owner_Id;

    public AccommodationDTO(
            Long id, String name, String description, LocationDTO location,
            AccommodationType type, Set<String> images, Set<AmenityDTO> amenities,
            Double rating, Integer minNumberOfGuests, Integer maxNumberOfGuests,
            Set<AvailabilityDTO> availability, Double pricePerNight,
            boolean pricePerGuest, Integer cancellationDeadline, Long owner_Id
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
        this.owner_Id=owner_Id;
    }

    public AccommodationDTO() {
    }



    public AccommodationDTO(Accommodation accommodation, AmenityService amenityService) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = new LocationDTO(accommodation.getLocation());
        this.type = accommodation.getType();
        this.images = accommodation.getImages();
        this.amenities = accommodation.getAmenities().stream()
                .map(amenity -> new AmenityDTO(amenity.getId(), amenity.getName(), amenity.getDescription(), amenity.getIcon(), amenityService))
                .collect(Collectors.toSet());
        this.rating = accommodation.getRating();
        this.minNumberOfGuests = accommodation.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.availability = accommodation.getAvailability().stream()
                .map(availability -> new AvailabilityDTO(availability.getId(), availability.getStartDate(), availability.getEndDate(), availability.getSpecialPrice(), availability.getAccommodation().getId()))
                .collect(Collectors.toSet());
        this.pricePerNight = accommodation.getPricePerNight();
        this.pricePerGuest = accommodation.getPricePerGuest();
        this.cancellationDeadline = accommodation.getCancellationDeadline();
        this.owner_Id=accommodation.getOwner().getId();
        for (Object imagePath:accommodation.getImages().toArray()){
        try{
            String imageBytes = imagesRepository.getImageBytes((String) imagePath /*"accommodations/accommodation-1/accommodation-1-1.jpg"*/);
            String imageType = imagesRepository.getImageType(imageBytes);
            this.imageTypes.add(imageType);
            this.imageBytes.add(imageBytes);
        } catch (Exception e) {
            this.imageTypes = new HashSet<>();
            this.imageBytes = new HashSet<>();
        }
        }
    }
}