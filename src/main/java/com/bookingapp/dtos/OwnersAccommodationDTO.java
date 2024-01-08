package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnersAccommodationDTO {
    private Long id;
    private String name;
    private String type;
    private int stars;
    private int maxGuests;
    private String address;
    private double price;
    private String imageType;
    private String mainPictureBytes;

    public OwnersAccommodationDTO(Long id, String name, String type, int stars, int maxGuests, String address, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stars = stars;
        this.maxGuests = maxGuests;
        this.address = address;
        this.price = price;
    }

    public OwnersAccommodationDTO() { }

}