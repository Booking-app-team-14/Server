package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyAccommodationReportDTO {
    private String month;
    private int numberOfReservations;
    private double totalProfit;

    // Konstruktor
    public MonthlyAccommodationReportDTO(String month, int numberOfReservations, double totalProfit) {
        this.month = month;
        this.numberOfReservations = numberOfReservations;
        this.totalProfit = totalProfit;
    }


}

