package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Location;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class AccommodationMobileDTO {
    @NotNull
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

    public AccommodationMobileDTO(Long id, String name, String type, int stars, int maxGuests, String address, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stars = stars;
        this.maxGuests = maxGuests;
        this.address = address;
        this.price = price;
    }

    public AccommodationMobileDTO(Accommodation acc) {
        this.id = acc.getId();
        this.name = acc.getName();
        this.type = String.valueOf(acc.getType());
        this.stars = acc.getRating().intValue();
        this.maxGuests = acc.getMaxNumberOfGuests();
        this.address = acc.getLocation().getAddress();
        this.price = acc.getPricePerNight();
    }

    public AccommodationMobileDTO() { }

}