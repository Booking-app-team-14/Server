package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.LocationDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Location;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping(name = "added location")
    public ResponseEntity<Long> addLocation(@RequestBody LocationDTO locationDTO) {
        Location location = new Location(locationDTO);
        locationService.save(location);
        return new ResponseEntity<>(location.getId(), HttpStatus.CREATED);
    }
}
