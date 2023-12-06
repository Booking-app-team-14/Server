package com.bookingapp.controllers;

import com.bookingapp.dtos.AvailabilityDTO;
import com.bookingapp.dtos.LocationDTO;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.Location;
import com.bookingapp.services.AvailabilityService;
import com.bookingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {
    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping(name = "added availability")
    public ResponseEntity<Long> addAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability(availabilityDTO);
        availabilityService.save(availability);
        return new ResponseEntity<>(availability.getId(), HttpStatus.CREATED);
    }
}
