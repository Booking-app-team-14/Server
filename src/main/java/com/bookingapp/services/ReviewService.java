package com.bookingapp.services;

import com.bookingapp.dtos.ReviewDTO;
import com.bookingapp.entities.Guest;
import com.bookingapp.entities.Owner;
import com.bookingapp.entities.Review;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.GuestRepository;
import com.bookingapp.repositories.OwnerRepository;
import com.bookingapp.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GuestRepository guestRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, GuestRepository guestRepository, OwnerRepository ownerRepository) {
        this.reviewRepository = reviewRepository;
        this.guestRepository = guestRepository;
        this.ownerRepository = ownerRepository;
    }

    public Review saveReview(ReviewDTO reviewDTO) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        //UserAccount user = (UserAccount) authentication.getPrincipal();
        //Optional<Guest> optionalGuest= (Optional<Guest>) authentication.getPrincipal();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        //Optional<Guest> optionalGuest = guestRepository.findById(reviewDTO.getSenderId());
        Optional<Guest> optionalGuest = guestRepository.findById(user.getId());
        Optional<Owner> optionalOwner = ownerRepository.findById(reviewDTO.getRecipientId());


        if (optionalGuest.isPresent() && optionalOwner.isPresent()) {
            Guest sender = optionalGuest.get();
            Owner recipient = optionalOwner.get();

            Review review = new Review(reviewDTO.getComment(), reviewDTO.getRating(), sender, recipient);
            return reviewRepository.save(review);
        } else {

            throw new EntityNotFoundException("Guest or Owner not found");
        }
    }

    public List<Review> getAllReviewsByOwnerId(Long ownerId) {
        return reviewRepository.findByRecipientId(ownerId);

    }
}

