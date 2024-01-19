package com.bookingapp.entities;

import com.bookingapp.dtos.AccommodationRequestDTO;
import com.bookingapp.entities.Accommodation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AccommodationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long accommodationId;
    @Size(min = 5, max = 100)
    private String name; // accommodation name
    @NotEmpty
    private String type; // accommodation type
    @NotEmpty
    private String ownerImageType; // owner profile picture type (jpg, png, etc.)
    @NotEmpty
    @Lob
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
