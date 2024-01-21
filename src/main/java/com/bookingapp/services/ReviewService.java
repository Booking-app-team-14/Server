package com.bookingapp.services;

import com.bookingapp.dtos.ReviewDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.exceptions.UnauthorizedAccessException;
import com.bookingapp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
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
    private ReservationRequestIRepository reservationRequestRepository;

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, GuestRepository guestRepository, OwnerRepository ownerRepository) {
        this.reviewRepository = reviewRepository;
        this.guestRepository = guestRepository;
        this.ownerRepository = ownerRepository;
    }

    /*public Review saveReview(ReviewDTO reviewDTO) {

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

            return review;
        } else {

            throw new EntityNotFoundException("Guest or Owner not found");
        }
    }*/

    public boolean hasAcceptedReservationForOwner(Long ownerId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        Optional<Guest> optionalGuest = guestRepository.findById(user.getId());

        if (optionalGuest.isPresent()  ) {
            Guest guest = optionalGuest.get();

            for (ReservationRequest reservationRequest : reservationRequestRepository.findAll()) {

                Owner owner = ownerRepository.findById(ownerId).get();

                if (reservationRequest.getUserId().equals(guest.getId()) &&
                        reservationRequest.getRequestStatus() == RequestStatus.ACCEPTED &&
                        reservationRequest.getEndDate().isBefore(java.time.LocalDate.now()) &&
                        reservationRequest.getUserUsername().equals(owner.getUsername())
                ) {
                    return true;
                }
            }

//            return reservationRequestRepository.hasAcceptedReservationForOwner(guest.getId(), ownerId);
        }
        return false;
    }

    public boolean hasAcceptedReservation  (Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        Optional<UserAccount> optionalUser = userRepository.findById(user.getId());


        if (optionalUser.isPresent()  ) {
            UserAccount userAcc = optionalUser.get();

            return reservationRequestRepository.hasAcceptedReservation (userAcc.getId(), id);
        }
        return false;
    }


    public Review saveReview(ReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        Optional<Guest> optionalGuest = guestRepository.findById(user.getId());
        Optional<Owner> optionalOwner = ownerRepository.findById(reviewDTO.getRecipientId());

        if (optionalGuest.isPresent() && optionalOwner.isPresent()) {
            Guest sender = optionalGuest.get();
            Owner recipient = optionalOwner.get();

            boolean hasAcceptedReservation = this.hasAcceptedReservationForOwner(recipient.getId());

            if (hasAcceptedReservation) {
                int ratingValue = ( reviewDTO.getRating() != 0) ? reviewDTO.getRating() : -1;

                Review review = new Review(reviewDTO.getComment(), ratingValue, sender, recipient);
                review.setApproved(false);
                reviewRepository.save(review);

                return review;
            } else {
                throw new ValidationException("Guest must have at least one accepted reservation for the specified owner  in the past.");
            }
        } else {
            throw new EntityNotFoundException("Guest or Owner not found");
        }
    }


    public List<Review> getAllReviewsByOwnerId(Long ownerId) {
        return reviewRepository.findByRecipientIdAndApproved(ownerId, true);

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
        List<Review> reviews = reviewRepository.findByRecipientIdAndApproved(ownerId, true);

        if (!reviews.isEmpty()) {
            double totalRating = 0;
            int size=0;
            for (Review review : reviews) {
                if (review.getRating() != -1) {
                totalRating += review.getRating();
                size++;
                }
            }

            return totalRating / size;
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

    public void deleteByReviewId(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public void sendNotificationForOwnerReview(Review review) {
        Owner owner = (Owner) userRepository.findByUsername(review.getRecipient().getUsername());
        for (NotificationType notWantedType : owner.getNotWantedNotificationTypes()){
            if (notWantedType.equals(NotificationType.OWNER_REVIEWED)){
                return;
            }
        }

        NotificationOwnerReviewed notification = new NotificationOwnerReviewed();
        notification.setType(NotificationType.OWNER_REVIEWED);
        notification.setReviewId(review.getId());
        notification.setReceiver(review.getRecipient());
        notification.setSender(review.getSender());
        notification.setSeen(false);
        notification.setSentAt(review.getTimestamp());
        notificationService.saveOwnerReviewed(notification);
        notificationService.sendNotification(review.getRecipient().getUsername());
    }

}

