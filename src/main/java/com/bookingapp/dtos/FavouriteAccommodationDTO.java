package com.bookingapp.dtos;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public FavouriteAccommodationDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.location = new LocationDTO(accommodation.getLocation());
        this.type = accommodation.getType();
        this.rating = accommodation.getRating();
        this.pricePerNight = accommodation.getPricePerNight();
        try{
            this.imageBytes = imagesRepository.getImageBytes(/*(String) accommodation.getImages().toArray()[0]*/ "accommodations/accommodation-1/accommodation_1.jpg");
            this.imageType = imagesRepository.getImageType(this.imageBytes);
        } catch (Exception e) {
            this.imageBytes = "";
            this.imageType = "png";
        }
    }

}
