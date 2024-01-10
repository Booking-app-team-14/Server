package com.bookingapp.dtos;

import com.bookingapp.entities.Amenity;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AmenityService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class AmenityDTO {

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
        ImagesRepository imagesRepository = new ImagesRepository();
        try {
            String imageBytes = imagesRepository.getImageBytes(icon);
            this.iconBytes = imageBytes;
            this.iconType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }
    }

    public static List<AmenityDTO> transformToDTO(List<Amenity> amenities, AmenityService amenityService) {
        return amenities.stream().map(amenity -> new AmenityDTO(amenity.getId(), amenity.getName(), amenity.getDescription(), amenity.getIcon(), amenityService)).toList();
    }

}
