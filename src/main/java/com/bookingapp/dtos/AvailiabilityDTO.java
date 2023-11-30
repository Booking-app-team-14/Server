package com.bookingapp.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AvailiabilityDTO {

    private Long id;
    private LocalDate start;
    private LocalDate end;
    private Double specialPrice;

    public AvailiabilityDTO(Long id, LocalDate start, LocalDate end, Double specialPrice) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.specialPrice = specialPrice;
    }
}
