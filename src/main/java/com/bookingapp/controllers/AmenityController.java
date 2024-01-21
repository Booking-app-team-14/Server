package com.bookingapp.controllers;

import com.bookingapp.dtos.AmenityDTO;
import com.bookingapp.dtos.LocationDTO;
import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Location;
import com.bookingapp.services.AmenityService;
import com.bookingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {
    @Autowired
    private AmenityService amenityService;

    @PostMapping(name = "added amenity")
    public ResponseEntity<Long> addAmenity(@RequestBody AmenityDTO amenityDTO) {
        Amenity amenity = new Amenity(amenityDTO);
        amenityService.save(amenity);
        return new ResponseEntity<>(amenity.getId(), HttpStatus.CREATED);
    }

    @GetMapping(name = "get all amenities")
    public ResponseEntity<List<AmenityDTO>> getAllAmenities() {
        return new ResponseEntity<>(amenityService.findAll(), HttpStatus.OK);
    }

}
