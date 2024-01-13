package com.bookingapp.entities;

import com.bookingapp.dtos.AccommodationRequestDTO;
import com.bookingapp.entities.Accommodation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AccommodationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accommodationId;
    private String name; // accommodation name
    private String type; // accommodation type
    private String ownerImageType; // owner profile picture type (jpg, png, etc.)
    @Lob
    private String ownerProfilePictureBytes;
    private String ownerUsername;
    private String dateRequested; // date requested, (epoch seconds)
    private String requestType; // request type (new, updated)
    private String message;
    private int stars;
    private String imageType; // accommodation main picture type (jpg, png, etc.)
    @Lob
    private String mainPictureBytes;
    @Lob
    @Column(nullable = true)
    private String serializedAccommodationUpdateDTO;

    public AccommodationRequest(AccommodationRequestDTO dto) {
        this.accommodationId = dto.getAccommodationId();
        this.name = dto.getName();
        this.type = dto.getType();
        this.ownerImageType = dto.getOwnerImageType();
        this.ownerProfilePictureBytes = dto.getOwnerProfilePictureBytes();
        this.ownerUsername = dto.getOwnerUsername();
        this.dateRequested = dto.getDateRequested();
        this.requestType = dto.getRequestType();
        this.message = dto.getMessage();
        this.stars = dto.getStars();
        this.imageType = dto.getImageType();
        this.mainPictureBytes = dto.getMainPictureBytes();
    }

    public AccommodationRequest(AccommodationRequestDTO dto, String serializedAccommodationUpdateDTO) {
        this.accommodationId = dto.getAccommodationId();
        this.name = dto.getName();
        this.type = dto.getType();
        this.ownerImageType = dto.getOwnerImageType();
        this.ownerProfilePictureBytes = dto.getOwnerProfilePictureBytes();
        this.ownerUsername = dto.getOwnerUsername();
        this.dateRequested = dto.getDateRequested();
        this.requestType = dto.getRequestType();
        this.message = dto.getMessage();
        this.stars = dto.getStars();
        this.imageType = dto.getImageType();
        this.mainPictureBytes = dto.getMainPictureBytes();
        this.serializedAccommodationUpdateDTO = serializedAccommodationUpdateDTO;
    }

    public AccommodationRequest(Long accommodationId, String name, String type, String ownerImageType, String ownerProfilePictureBytes, String ownerUsername, String dateRequested, String requestType, String message, int stars, String imageType, String mainPictureBytes) {
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

    public AccommodationRequest() { }

}
