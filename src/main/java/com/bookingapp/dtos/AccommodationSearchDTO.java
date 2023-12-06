package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.AccommodationType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class AccommodationSearchDTO {

    private Long id;
    private String name;
    private String description;
    private AccommodationType accommodationType;
    private String image;
    private Double rating;
    private Integer maxNumberOfGuests;
    private Double pricePerNight;

    public AccommodationSearchDTO(Long id, String name, String description, AccommodationType accommodationType,
                            String image, Double rating, Integer maxNumberOfGuests, Double pricePerNight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.accommodationType = accommodationType;
        this.image = image;
        this.rating = rating;
        this.maxNumberOfGuests = maxNumberOfGuests;
        this.pricePerNight = pricePerNight;
    }

    public AccommodationSearchDTO() {
    }



    public AccommodationSearchDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.accommodationType= accommodation.getType();
        this.image = (String) new ArrayList<>(List.of(accommodation.getImages().toArray())).get(0);
        this.rating = accommodation.getRating();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.pricePerNight = accommodation.getPricePerNight();
    }

}

