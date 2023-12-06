package com.bookingapp.services;

import com.bookingapp.entities.Availability;
import com.bookingapp.entities.Location;
import com.bookingapp.repositories.AvailabilityRepository;
import com.bookingapp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    public void save(Availability availability) {availabilityRepository.save(availability);
    }
}
