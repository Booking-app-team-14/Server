package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.AvailabilityDTO;
import com.bookingapp.dtos.LocationDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.Location;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.AvailabilityService;
import com.bookingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/availabilities/")
public class AvailabilityController {
    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private AccommodationService accommodationService;

    @PostMapping(name = "added availability")
    public ResponseEntity<Long> addAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability(availabilityDTO);
        availabilityService.save(availability);
        return new ResponseEntity<>(availability.getId(), HttpStatus.CREATED);
    }

    @GetMapping(value = "accommodations/{id}")
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilityOfAccommodation(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
        {

            AccommodationDTO result = new AccommodationDTO(accommodation.get(), accommodationService);

            return new ResponseEntity<>(result.getAvailability().stream().toList(), HttpStatus.OK);

        }
    }
}
