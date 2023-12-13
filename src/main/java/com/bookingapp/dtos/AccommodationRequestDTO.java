package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationRequestDTO {

    private Long id;
    private String name;
    private String address;
    private String description;
    private String location;
    private String type;
    private double price;
    private int postedAgo;
    private String ownerImageType;
    private byte[] ownerProfilePictureBytes;
    private String ownerUsername;
    private String dateRequested;
    private String requestType;
    private String message;
    private int stars;
    private String imageType;
    private byte[] mainPictureBytes;

    public AccommodationRequestDTO(Long id, String name, String address, String description, String location, String type, double price, int postedAgo, String ownerImageType, byte[] ownerProfilePictureBytes, String ownerUsername, String dateRequested, String requestType, String message, int stars, String imageType, byte[] mainPictureBytes){
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.location = location;
        this.type = type;
        this.price = price;
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
