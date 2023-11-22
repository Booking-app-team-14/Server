package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyReportDTO {

    private Long id;
    private Long accommodationId;
    private String accommodationName;
    private int year;
    private String month;


    public MonthlyReportDTO(Long id, Long accommodationId, String accommodationName, int year, String month) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.accommodationName = accommodationName;
        this.year = year;
        this.month=month;

    }


}
