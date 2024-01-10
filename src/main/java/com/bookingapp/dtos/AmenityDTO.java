package com.bookingapp.dtos;

import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AmenityService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class AmenityDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    private Long id;
    private String name;
    private String description;
    private String icon;
    protected String iconType;
    protected String iconBytes;

    public AmenityDTO(Long id, String name, String description, String icon, AmenityService amenityService) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon= amenityService.findAmenityImageName(this.id);
        try {
            String imageBytes = imagesRepository.getImageBytes(icon);
            this.iconBytes = imageBytes;
            this.iconType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }
    }


}
