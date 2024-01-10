package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {

    private String imageBytes;
    private String imageType;

    public Image(String imageBytes, String imageType) {
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    public Image() {
    }

}
