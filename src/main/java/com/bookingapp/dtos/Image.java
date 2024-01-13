package com.bookingapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Image implements Serializable {

    private String imageBytes;
    private String imageType;

    public Image(String imageBytes, String imageType) {
        this.imageBytes = imageBytes;
        this.imageType = imageType;
    }

    public Image() {
    }

}
