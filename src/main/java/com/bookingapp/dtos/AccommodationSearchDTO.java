package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class AccommodationSearchDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    private Long id;
    private String name;
    private String description;
    private AccommodationType accommodationType;
    protected String imageType;
    protected String imageBytes;
    private Double rating;
    private Integer maxNumberOfGuests;
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



    public AccommodationSearchDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.accommodationType= accommodation.getType();
        try{
            this.imageBytes = imagesRepository.getImageBytes(/*(String) accommodation.getImages().toArray()[0]*/ "accommodations/accommodation-1/accommodation-1-1.jpg");
            this.imageType = imagesRepository.getImageType(this.imageBytes);
        } catch (Exception e) {
            this.imageBytes = "";
            this.imageType = "png";
        }
        this.rating = accommodation.getRating();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.pricePerNight = accommodation.getPricePerNight();
        this.approved = accommodation.isApproved();
    }

}

