package com.bookingapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyAccommodationReportDTO {

    @NotEmpty
    private String month;
    @Min(value = 0)
    private int numberOfReservations;
    @Min(value = 0)
    private double totalProfit;

    public MonthlyAccommodationReportDTO(String month, int numberOfReservations, double totalProfit) {
        this.month = month;
        this.numberOfReservations = numberOfReservations;
        this.totalProfit = totalProfit;
    }

}

