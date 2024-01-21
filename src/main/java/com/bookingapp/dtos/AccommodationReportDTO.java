package com.bookingapp.dtos;


import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationReport;
import com.bookingapp.entities.Location;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.services.AccommodationService;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccommodationReportDTO {

    @Size(min = 5, max = 100)
    private String accommodationName;
    @NotNull
    private AccommodationType type;
    @NotEmpty
    protected String imageType;
    @NotEmpty
    protected String imageBytes;
    @Min(value = -1)
    @Max(value = 5)
    private Double rating;
    @Min(value = 1)
    private Integer minNumberOfGuests;
    @Min(value = 1)
    private Integer maxNumberOfGuests;
    @Min(value = 1)
    private Double pricePerNight;
    @Min(value = 0)
    private int numberOfReservations;
    @Min(value = 0)
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
        ImagesRepository imagesRepository = new ImagesRepository();
        String accommodationImagePath = accommodationService.findAccommodationImageName(accommodation.getId());
        try {
            String imageBytes = imagesRepository.getImageBytes(accommodationImagePath);
            this.imageBytes = imageBytes;
            this.imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException ignored) { }

    }

}
