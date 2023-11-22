package com.bookingapp.services;

import com.bookingapp.entities.OwnerReview;
import com.bookingapp.repositories.OwnerReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerReviewService {

    @Autowired
    private OwnerReviewRepository ownerReviewRepository;

    public void save(OwnerReview ownerReview) {
        ownerReviewRepository.save(ownerReview);
    }

    public List<OwnerReview> findAllApprovedByOwnerId(Long ownerId) {
        return ownerReviewRepository.findAllApprovedByOwnerId(ownerId);
    }

    public List<OwnerReview> findAllPending() {
        return ownerReviewRepository.findAllPending();
    }

    public OwnerReview findById(Long id) {
        return ownerReviewRepository.findById(id).orElse(null);
    }

}
