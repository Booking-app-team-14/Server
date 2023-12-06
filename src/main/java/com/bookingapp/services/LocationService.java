package com.bookingapp.services;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Location;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository accommodationRepository;

    public void save(Location location) {
        accommodationRepository.save(location);
    }
}
