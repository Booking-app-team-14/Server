package com.bookingapp.services;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

}