package com.bookingapp.services;

import com.bookingapp.entities.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationImageService {
    @Autowired
    private  AccommodationService accommodationService;


    public void saveImageForAccommodation(Long accommodationId, MultipartFile file) {

        /* byte[] imageData = // Convert file to byte[] or handle as per your requirements
         */        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(accommodationId);
        //accommodation.getImages().add(imageData);
       // accommodationService.save(accommodation);
    }

    public List<String> getAccommodationImages(Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(accommodationId);
        return new ArrayList<>(accommodation.get().getImages());
    }
}
