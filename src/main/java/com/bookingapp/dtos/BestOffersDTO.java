package com.bookingapp.dtos;


import com.bookingapp.enums.AccommodationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BestOffersDTO {

    @NotNull
    private Long Id;
    @NotEmpty
    private String image;
    @NotNull
    private AccommodationType type;
    @Min(value = 1)
    private int maxNumberOfGuests;
    @Min(value = 1)
    private double pricePerNight;
    @NotEmpty
    private String description;

    public BestOffersDTO(Long id, String image, AccommodationType type, int maxNumberOfGuests, double pricePerNight, String description){
        this.Id= id;
        this.image=image;
        this.type=type;
        this.maxNumberOfGuests=maxNumberOfGuests;
        this.pricePerNight=pricePerNight;
        this.description=description;
    }

}
