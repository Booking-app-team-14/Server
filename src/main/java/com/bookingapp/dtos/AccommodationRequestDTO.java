package com.bookingapp.dtos;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationRequestDTO {

    private Long id;
    private String name; // accommodation name
    private String type; // accommodation type
    private int postedAgo; // minutes since requested
    private String ownerImageType; // owner profile picture type (jpg, png, etc.)
    private byte[] ownerProfilePictureBytes;
    private String ownerUsername;
    private String dateRequested; // date requested, string formatted
    private String requestType; // request type (new, updated)
    private String message;
    private int stars;
    private String imageType; // accommodation main picture type (jpg, png, etc.)
    private byte[] mainPictureBytes;

    public AccommodationRequestDTO(Long id, String name, String type, int postedAgo, String ownerImageType, byte[] ownerProfilePictureBytes, String ownerUsername, String dateRequested, String requestType, String message, int stars, String imageType, byte[] mainPictureBytes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.postedAgo = postedAgo;
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

    public AccommodationRequestDTO() { }

}
