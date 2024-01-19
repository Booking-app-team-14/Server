package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.enums.Handling;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.AmenityService;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccommodationDTO {

    @NotNull
    private Long id;
    @Size(min = 5, max = 100)
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private LocationDTO location;
    @NotNull
    private AccommodationType type;
    @NotEmpty
    private Set<String> images = new HashSet<>();
    @NotEmpty
    private List<String> imageTypes = new ArrayList<>();
    @NotEmpty
    private Set<String> imageBytes = new HashSet<>();
    @NotNull
    private Set<AmenityDTO> amenities;
    @Min(value = -1)
    @Max(value = 5)
    private Double rating;
    @Min(value = 1)
    private Integer minNumberOfGuests;
    @Min(value = 1)
    private Integer maxNumberOfGuests;
    @NotNull
    private Set<AvailabilityDTO> availability;
    @Min(value = 1)
    private Double pricePerNight;
    private boolean pricePerGuest;
    private boolean automatic;
    @NotNull
    @Min(value = 0)
    private Integer cancellationDeadline;
    @NotNull
    private Long owner_Id;

    public AccommodationDTO(
            Long id, String name, String description, LocationDTO location,
            AccommodationType type, Set<String> images, Set<AmenityDTO> amenities,
            Double rating, Integer minNumberOfGuests, Integer maxNumberOfGuests,
            Set<AvailabilityDTO> availability, Double pricePerNight,
            boolean pricePerGuest,boolean automatic, Integer cancellationDeadline, Long owner_Id
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
        this.automatic = automatic;
        this.cancellationDeadline = cancellationDeadline;
        this.owner_Id=owner_Id;
    }

    public AccommodationDTO() {
    }



    public AccommodationDTO(Accommodation accommodation, AccommodationService accommodationService) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = new LocationDTO(accommodation.getLocation());
        this.type = accommodation.getType();
        this.images = accommodation.getImages();
        this.amenities = accommodation.getAmenities().stream()
                .map(amenity -> new AmenityDTO(amenity.getId(), amenity.getName(), amenity.getDescription()))
                .collect(Collectors.toSet());
        this.rating = accommodation.getRating();
        this.minNumberOfGuests = accommodation.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.availability = accommodation.getAvailability().stream()
                .map(availability -> new AvailabilityDTO(availability.getId(), availability.getStartDate(), availability.getEndDate(), availability.getSpecialPrice(), availability.getAccommodation().getId()))
                .collect(Collectors.toSet());
        this.pricePerNight = accommodation.getPricePerNight();
        this.pricePerGuest = accommodation.getPricePerGuest();
        this.automatic = accommodation.isAutomatic();
        this.cancellationDeadline = accommodation.getCancellationDeadline();
        this.owner_Id=accommodation.getOwner().getId();
        ImagesRepository imagesRepository = new ImagesRepository();
        try{
            List<String> imageBytes = accommodationService.getAllAccommodationImages(this.id);
            for (String imageByte:imageBytes) {
                String imageType = imagesRepository.getImageType(imageByte);
                this.imageTypes.add(imageType);
                this.imageBytes.add(imageByte);
            }
        } catch (Exception e) {
            this.imageTypes = new ArrayList<>();
            this.imageBytes = new HashSet<>();
        }
    }
}