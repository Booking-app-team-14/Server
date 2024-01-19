package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationRequest;
import com.bookingapp.entities.Owner;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.UserAccountService;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AccommodationRequestDTO {

    @NotNull
    private Long accommodationId;
    @Size(min = 5, max = 100)
    private String name; // accommodation name
    @NotEmpty
    private String type; // accommodation type
    @NotEmpty
    private String ownerImageType; // owner profile picture type (jpg, png, etc.)
    @NotEmpty
    private String ownerProfilePictureBytes;
    @Size(min = 5, max = 50)
    private String ownerUsername;
    @NotEmpty
    private String dateRequested; // date requested, (epoch seconds)
    @NotEmpty
    private String requestType; // request type (new, updated)
    @NotEmpty
    private String message;
    @Min(value = -1)
    private int stars;
    @NotEmpty
    private String imageType; // accommodation main picture type (jpg, png, etc.)
    @NotEmpty
    private String mainPictureBytes;

    public AccommodationRequestDTO(Long accommodationId, String name, String type, String ownerImageType, String ownerProfilePictureBytes, String ownerUsername, String dateRequested, String requestType, String message, int stars, String imageType, String mainPictureBytes) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.type = type;
        this.ownerImageType = ownerImageType;
        this.ownerProfilePictureBytes = ownerProfilePictureBytes;
        this.ownerUsername = ownerUsername;
        this.dateRequested = dateRequested;
        this.requestType = requestType;
        this.message = message;
        this.stars = stars;
        this.imageType = imageType;
        this.mainPictureBytes = mainPictureBytes;
    }

    public AccommodationRequestDTO(Accommodation accommodation) {
        this.accommodationId = accommodation.getId();
        this.name = accommodation.getName();
        this.type = accommodation.getType().toString();
        this.stars = accommodation.getRating().intValue();
    }

    public AccommodationRequestDTO(AccommodationRequest request) {
        this.accommodationId = request.getAccommodationId();
        this.name = request.getName();
        this.type = request.getType();
        this.ownerImageType = request.getOwnerImageType();
        this.ownerProfilePictureBytes = request.getOwnerProfilePictureBytes();
        this.ownerUsername = request.getOwnerUsername();
        this.dateRequested = request.getDateRequested();
        this.requestType = request.getRequestType();
        this.message = request.getMessage();
        this.stars = request.getStars();
        this.imageType = request.getImageType();
        this.mainPictureBytes = request.getMainPictureBytes();
    }

    public AccommodationRequestDTO() { }

}
