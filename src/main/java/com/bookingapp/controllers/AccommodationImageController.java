package com.bookingapp.controllers;

import com.bookingapp.services.AccommodationImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/accommodations/{accommodationId}/images")
public class AccommodationImageController {

    @Autowired
    private  AccommodationImageService imageService;

    // Inject AccommodationImageService via constructor

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long accommodationId,
            @RequestParam("imageFile") MultipartFile file
    ) {
        // Validate and handle uploaded file
        imageService.saveImageForAccommodation(accommodationId, file);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @GetMapping
    public ResponseEntity<List<String>> getAccommodationImages(@PathVariable Long accommodationId) {
        List<String> images = imageService.getAccommodationImages(accommodationId);
        return ResponseEntity.ok(images);
    }
}
