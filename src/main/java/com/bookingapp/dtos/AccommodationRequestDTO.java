package com.bookingapp.dtos;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationRequest;
import com.bookingapp.entities.Owner;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.UserAccountService;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationRequestDTO {

    private Long accommodationId;
    private String name; // accommodation name
    private String type; // accommodation type
    private String ownerImageType; // owner profile picture type (jpg, png, etc.)
    private String ownerProfilePictureBytes;
    private String ownerUsername;
    private String dateRequested; // date requested, (epoch seconds)
    private String requestType; // request type (new, updated)
    private String message;
    private int stars;
    private String imageType; // accommodation main picture type (jpg, png, etc.)
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
