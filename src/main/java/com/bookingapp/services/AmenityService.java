package com.bookingapp.services;

import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Location;
import com.bookingapp.repositories.AmenityRepository;
import com.bookingapp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public void save(Amenity amenity) {
        amenityRepository.save(amenity);
    }
}
