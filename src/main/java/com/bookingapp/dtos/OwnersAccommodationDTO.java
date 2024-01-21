package com.bookingapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnersAccommodationDTO {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String type;
    @Min(value = -1)
    private int stars;
    @Min(value = 1)
    private int maxGuests;
    @NotEmpty
    private String address;
    @Min(value = 0)
    private double price;
    @NotEmpty
    private String imageType;
    @NotEmpty
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