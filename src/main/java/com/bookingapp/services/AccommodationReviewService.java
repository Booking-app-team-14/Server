package com.bookingapp.services;

import com.bookingapp.dtos.AccommodationReviewDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.ReviewStatus;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.AccommodationReviewRepository;
import com.bookingapp.repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationReviewService {

    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private GuestRepository userRepository;

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

    public AccommodationReview saveAccommodationReview(AccommodationReviewDTO reviewDTO) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();
        Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(reviewDTO.getAccommodationId());

        Optional<Guest> optionalGuest = userRepository.findById(user.getId());
        //Optional<Owner> optionalOwner = ownerRepository.findById(reviewDTO.getRecipientId());


        //if (user.isPresent() && optionalOwner.isPresent()) {

        if ( optionalGuest.isPresent()  && optionalAccommodation.isPresent()) {
            Guest sender = optionalGuest.get();
            Accommodation accommodation = optionalAccommodation.get();


            AccommodationReview review = new AccommodationReview(accommodation, sender,  reviewDTO.getRating(), reviewDTO.getComment(), ReviewStatus.PENDING,
                    LocalDateTime.now());
            return accommodationReviewRepository.save(review);
        } else {
            throw new EntityNotFoundException("Guest or Accommodation not found");
        }
        //} else {

        //throw new EntityNotFoundException("Guest or Owner not found");
        //}
    }

    public List<AccommodationReview> getPendingAccommodationReviewsByAccommodation(Long accommodationId) {
        return accommodationReviewRepository.findByStatusAndAccommodationId( accommodationId);
    }

    public Optional<AccommodationReview> getReviewById(Long reviewId) {
        return accommodationReviewRepository.findById(reviewId);
    }

    public void deleteReviewById(Long reviewId) {
        Optional<AccommodationReview> review= getReviewById(reviewId);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();
        if (user.getId()==review.get().getUser().getId()){
            accommodationReviewRepository.deleteById(reviewId);
        }
        else {
            throw new UnauthorizedAccessException("You are not authorized to delete this review");
        }
    }
}
