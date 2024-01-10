package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationUpdateDTO {

    AccommodationDTO accommodationDTO;
    String message;

    public AccommodationUpdateDTO(AccommodationDTO accommodationDTO, String message) {
        this.accommodationDTO = accommodationDTO;
        this.message = message;
    }

    public AccommodationUpdateDTO() {
    }

}
