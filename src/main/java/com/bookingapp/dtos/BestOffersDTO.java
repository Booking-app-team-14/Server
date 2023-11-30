package com.bookingapp.dtos;


import com.bookingapp.enums.AccommodationType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BestOffersDTO {

    private long Id;

    private String image;

    private AccommodationType type;

    private int maxNumberOfGuests;

    private double pricePerNight;

    private String description;

    public BestOffersDTO(Long id, String image, AccommodationType type, int maxNumberOfGuests, double pricePerNight, String description){
        this.Id= id;
        this.image=image;
        this.type=type;
        this.maxNumberOfGuests=maxNumberOfGuests;
        this.pricePerNight=pricePerNight;
        this.description=description;
    }

}
