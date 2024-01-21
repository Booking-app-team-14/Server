package com.bookingapp.dtos;

import com.bookingapp.entities.Amenity;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AmenityService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class AmenityDTO {

    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String icon;
    protected String iconType;
    protected String iconBytes;

    public AmenityDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = "amenities\\icon" + id + ".jpg";
        ImagesRepository imagesRepository = new ImagesRepository();
        try {
            String imageBytes = imagesRepository.getImageBytes(this.icon);
            this.iconBytes = imageBytes;
            this.iconType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }
    }

    public static List<AmenityDTO> transformToDTO(List<Amenity> amenities) {
        return amenities.stream().map(amenity -> new AmenityDTO(amenity.getId(), amenity.getName(), amenity.getDescription())).toList();
    }

}
