package com.bookingapp.services;

import com.bookingapp.entities.Amenity;
import com.bookingapp.repositories.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public void save(Amenity amenity) {
        amenityRepository.save(amenity);
    }

    public List<Amenity> findAllById(Set<Long> amenityIds) {
        return amenityRepository.findAllById(amenityIds);
    }
}
