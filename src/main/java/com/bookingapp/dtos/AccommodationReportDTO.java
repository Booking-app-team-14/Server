package com.bookingapp.dtos;


import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationReport;
import com.bookingapp.entities.Location;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccommodationReportDTO {

    private ImagesRepository imagesRepository = new ImagesRepository();

    private String accommodationName;

    private AccommodationType type;

    protected String imageType;

    protected String imageBytes;

    private Double rating;

    private Integer minNumberOfGuests;

    private Integer maxNumberOfGuests;

    private Double pricePerNight;

    private int numberOfReservations;

    private double totalProfit;
    public AccommodationReportDTO() {
    }

    public AccommodationReportDTO(Accommodation accommodation, int numberOfReservations, double totalProfit, AccommodationService accommodationService) {
        this.accommodationName = accommodation.getName();
        this.type = accommodation.getType();
        this.rating = accommodation.getRating();
        this.minNumberOfGuests = accommodation.getMinNumberOfGuests();
        this.maxNumberOfGuests = accommodation.getMaxNumberOfGuests();
        this.pricePerNight = accommodation.getPricePerNight();
        this.numberOfReservations = numberOfReservations;
        this.totalProfit = totalProfit;

        String accommodationImagePath = accommodationService.findAccommodationImageName(accommodation.getId());
        try {
            String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
            this.imageBytes = imageBytes;
            this.imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }

    }


}
