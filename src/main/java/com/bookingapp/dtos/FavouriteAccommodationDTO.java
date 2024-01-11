package com.bookingapp.dtos;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class FavouriteAccommodationDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    private Long id;
    private String name;
    private LocationDTO location;
    private AccommodationType type;
    protected String imageType;
    protected String imageBytes;
    private Double rating;
    private Double pricePerNight;

    public FavouriteAccommodationDTO(Accommodation accommodation, AccommodationService accommodationService) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.location = new LocationDTO(accommodation.getLocation());
        this.type = accommodation.getType();
        this.rating = accommodation.getRating();
        this.pricePerNight = accommodation.getPricePerNight();
        String accommodationImagePath = accommodationService.findAccommodationImageName(accommodation.getId());
        try {
            String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
            this.imageBytes = imageBytes;
            this.imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }
    }

}
