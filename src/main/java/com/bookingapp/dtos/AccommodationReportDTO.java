package com.bookingapp.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccommodationReportDTO {

    private Long id;
    private Long accommodationId;
    private String accommodationName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfReservations;
    private double totalProfit;
    public AccommodationReportDTO() {
    }

    public AccommodationReportDTO(Long id, Long accommodationId, String accommodationName, LocalDate startDate, LocalDate endDate, int numberOfReservations, double totalProfit) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.accommodationName = accommodationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfReservations = numberOfReservations;
        this.totalProfit = totalProfit;
    }

}
