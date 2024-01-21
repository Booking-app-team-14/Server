package com.bookingapp.services;

import com.bookingapp.entities.Amenity;
import com.bookingapp.entities.Availability;
import com.bookingapp.repositories.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    public void save(Availability availability) {availabilityRepository.save(availability);
    }

    public List<Availability> findAllById(Set<Long> amenityIds) {
        return availabilityRepository.findAllById(amenityIds);
    }

    public Availability findById(Long id) {
        return availabilityRepository.findById(id).get();
    }
}
