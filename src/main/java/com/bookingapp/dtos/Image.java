package com.bookingapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Image implements Serializable {

    @NotEmpty
    private String imageBytes;
    @NotEmpty
    private String imageType;

    public Image(String imageBytes, String imageType) {
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    public Image() {
    }

}
