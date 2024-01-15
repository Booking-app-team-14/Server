package com.bookingapp.services;

import com.bookingapp.dtos.ReviewDTO;
import com.bookingapp.entities.*;
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
        UserAccount user = (UserAccount) authentication.getPrincipal();
        Optional<Guest> optionalGuest = guestRepository.findById(user.getId());
        Optional<Owner> optionalOwner = ownerRepository.findById(reviewDTO.getRecipientId());


        if (optionalGuest.isPresent() && optionalOwner.isPresent()) {
            Guest sender = optionalGuest.get();
            Owner recipient = optionalOwner.get();

            Review review = new Review(reviewDTO.getComment(), reviewDTO.getRating(), sender, recipient);
            review.setApproved(false);
            reviewRepository.save(review);
            // TODO: admin has to approve the review

            return review;
        } else {

            throw new EntityNotFoundException("Guest or Owner not found");
        }
    }

    public List<Review> getAllReviewsByOwnerId(Long ownerId) {
        return reviewRepository.findByRecipientId(ownerId);

    }

    public Optional<Review> getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public void deleteReviewById(Long reviewId) {
        Optional<Review> review= getReviewById(reviewId);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();
        if (user.getId()==review.get().getSender().getId()){
        reviewRepository.deleteById(reviewId);
        }
        else {
            throw new UnauthorizedAccessException("You are not authorized to delete this review");
        }
    }

    public Double getAverageRatingByOwnerId(Long ownerId) {
        List<Review> reviews = reviewRepository.findByRecipientId(ownerId);

        if (!reviews.isEmpty()) {
            double totalRating = 0;
            for (Review review : reviews) {
                totalRating += review.getRating();
            }

            return totalRating / reviews.size();
        } else {
            return null;
        }
    }

    public List<Review> getAllPendingOwnerReviews() {
        return reviewRepository.findByApprovedFalse();
    }

    public void save(Review updatedReview) {
        reviewRepository.save(updatedReview);
    }

    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    public  Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

}

