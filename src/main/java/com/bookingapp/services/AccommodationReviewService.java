package com.bookingapp.services;

import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.repositories.AccommodationReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationReviewService {

    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    public List<AccommodationReview> findAll() {
        return accommodationReviewRepository.findAll();
    }

    public AccommodationReview findById(Long id) {
        return accommodationReviewRepository.findById(id).orElse(null);
    }

    public void save(AccommodationReview accommodationReview) {
        accommodationReviewRepository.save(accommodationReview);
    }

    public List<AccommodationReview> findAllApprovedByAccommodationId(Long id) {
        return accommodationReviewRepository.findAllApprovedByAccommodationId(id);
    }

    public List<AccommodationReview> findAllPending() {
        return accommodationReviewRepository.findAllPending();
    }

}
