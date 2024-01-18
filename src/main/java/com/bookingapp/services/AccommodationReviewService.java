package com.bookingapp.services;

import com.bookingapp.dtos.AccommodationReviewDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.enums.ReviewStatus;
import com.bookingapp.exceptions.ReviewNotAllowedException;
import com.bookingapp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private GuestRepository guestRepository;

    @Autowired
    private ReservationRequestIRepository reservationRequestRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserAccountRepository userRepository;

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

   /* public AccommodationReview saveAccommodationReview(AccommodationReviewDTO reviewDTO) {

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
    }*/
   public AccommodationReview saveAccommodationReview(AccommodationReviewDTO reviewDTO) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       UserAccount user = (UserAccount) authentication.getPrincipal();
       Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(reviewDTO.getAccommodationId());
       Optional<Guest> optionalGuest = guestRepository.findById(user.getId());

       if (optionalGuest.isPresent() && optionalAccommodation.isPresent()) {
           Guest sender = optionalGuest.get();
           Accommodation accommodation = optionalAccommodation.get();


           //if (hasAcceptedReservation(accommodation.getId())) {
               if (isWithinSevenDaysFromEnd(reviewDTO.getAccommodationId() )) {
                   AccommodationReview review = new AccommodationReview(accommodation, sender, reviewDTO.getRating(),
                           reviewDTO.getComment(), ReviewStatus.PENDING, LocalDateTime.now());
                   return accommodationReviewRepository.save(review);
               } else {
                   throw new ReviewNotAllowedException("Review cannot be submitted without an accepted reservation request in the past more than 7 days after the reservation ends.");
               }
           //} else {
               //throw new ReviewNotAllowedException("Review cannot be submitted without an accepted reservation request in the past.");
           //}
       } else {
           throw new EntityNotFoundException("Guest or Accommodation not found");
       }
   }

    public boolean hasAcceptedReservation(Long accommodationId) {
        LocalDate today = LocalDate.now();

        return reservationRequestRepository.existsByAccommodationIdAndRequestStatusAndEndDateBefore(
                accommodationId, RequestStatus.ACCEPTED, today);
    }



    public boolean isWithinSevenDaysFromEnd(Long accommodationId ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        List<ReservationRequest> reservationRequest = reservationRequestRepository
                .findAllByAccommodationIdAndUserIdAndRequestStatusOrderByEndDateDesc(
                        accommodationId, user.getId(), RequestStatus.ACCEPTED);

        LocalDate now = LocalDate.now();
        for (ReservationRequest request : reservationRequest) {
            LocalDate endDate = request.getEndDate();
            if (endDate.isBefore(now) && now.isBefore(endDate.plusDays(7)) ){
                return true;
            }
        }
        return false;

//        return reservationRequest.map(request -> {
//            LocalDate endDate = request.getEndDate();
//            LocalDate now = LocalDate.now();
//
//            return (endDate.isBefore(now) && now.isBefore(endDate.plusDays(7)) );
//        }).orElse(false);
    }


    public List<AccommodationReview> getApprovedAccommodationReviewsByAccommodation(Long accommodationId) {
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

    public void deleteById(Long accommodationId) {
        accommodationReviewRepository.deleteById(accommodationId);
    }



    public Double getAverageRatingByAccommodationId(Long accId) {
        List<AccommodationReview> reviews = accommodationReviewRepository.findByStatusAndAccommodationId(accId) ;

        if (!reviews.isEmpty()) {
            double totalRating = 0;
            for (AccommodationReview review : reviews) {
                totalRating += review.getRating();
            }

            return totalRating / reviews.size();
        } else {
            return null;
        }
    }

    public void delete(AccommodationReview review) {
        accommodationReviewRepository.delete(review);
    }

    public void sendNotificationForAccommodationReview(AccommodationReview updatedAccommodationReview) {
        Owner owner = (Owner) userRepository.findByUsername(updatedAccommodationReview.getAccommodation().getOwner().getUsername());
        for (NotificationType notWantedType : owner.getNotWantedNotificationTypes()){
            if (notWantedType.equals(NotificationType.ACCOMMODATION_REVIEWED)){
                return;
            }
        }

        NotificationAccommodationReviewed notification = new NotificationAccommodationReviewed();
        notification.setAccommodationReviewId(updatedAccommodationReview.getId());
        notification.setSender(updatedAccommodationReview.getUser());
        notification.setReceiver(updatedAccommodationReview.getAccommodation().getOwner());
        notification.setSentAt(LocalDateTime.now());
        notification.setSeen(false);
        notification.setType(NotificationType.ACCOMMODATION_REVIEWED);
        notificationService.saveAccommodationsReviewed(notification);
    }

}
