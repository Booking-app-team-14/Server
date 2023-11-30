package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyReportDTO {

    private Long id;
    private Long accommodationId;
    private int year;
    private String month;

    public MonthlyReportDTO() {
    }


    public MonthlyReportDTO(Long id, Long accommodationId, int year, String month) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.year = year;
        this.month=month;

    }


}
