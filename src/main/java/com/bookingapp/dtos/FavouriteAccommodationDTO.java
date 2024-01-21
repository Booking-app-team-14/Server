package com.bookingapp.dtos;
import com.bookingapp.entities.Accommodation;
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

@Getter
@Setter
public class FavouriteAccommodationDTO {

    @NotNull
    private Long id;
    @Size(min = 5, max = 100)
    private String name;
    @NotNull
    private LocationDTO location;
    @NotNull
    private AccommodationType type;
    @NotEmpty
    protected String imageType;
    @NotEmpty
    protected String imageBytes;
    @Min(value = -1)
    private Double rating;
    @Min(value = 1)
    private Double pricePerNight;

    public FavouriteAccommodationDTO(Accommodation accommodation, AccommodationService accommodationService) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.location = new LocationDTO(accommodation.getLocation());
        this.type = accommodation.getType();
        this.rating = accommodation.getRating();
        this.pricePerNight = accommodation.getPricePerNight();
        ImagesRepository imagesRepository = new ImagesRepository();
        String accommodationImagePath = accommodationService.findAccommodationImageName(accommodation.getId());
        try {
            String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
            this.imageBytes = imageBytes;
            this.imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }
    }

}
