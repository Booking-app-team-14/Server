package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class AccommodationSearchDTO {

    @NotNull
    private Long id;
    @Size(min = 5, max = 100)
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private AccommodationType accommodationType;
    @NotEmpty
    protected String imageType;
    @NotEmpty
    protected String imageBytes;
    @Min(value = -1)
    private Double rating;
    @Min(value = 1)
    private Integer maxNumberOfGuests;
    @Min(value = 1)
    private Double pricePerNight;
    private boolean approved;

    public AccommodationSearchDTO(Long id, String name, String description, AccommodationType accommodationType,
                            String imageType,String imageBytes, Double rating, Integer maxNumberOfGuests, Double pricePerNight, boolean approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accommodationType = accommodationType;
        this.imageType = imageType;
        this.imageBytes = imageBytes;
        this.rating = rating;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.pricePerNight = pricePerNight;
        this.approved = approved;
    }

    public AccommodationSearchDTO() {
    }

    public AccommodationSearchDTO(Accommodation accommodation, AccommodationService accommodationService) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.accommodationType= accommodation.getType();
        ImagesRepository imagesRepository = new ImagesRepository();
        String accommodationImagePath = accommodationService.findAccommodationImageName(accommodation.getId());
        try {
            String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
            this.imageBytes = imageBytes;
            this.imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }
        this.rating = accommodation.getRating();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.pricePerNight = accommodation.getPricePerNight();
        this.approved = accommodation.isApproved();
    }

}

