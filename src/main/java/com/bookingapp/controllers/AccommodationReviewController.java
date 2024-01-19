package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationReviewDTO;
import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.enums.ReviewStatus;
import com.bookingapp.repositories.ReviewReportRepository;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.ReviewService;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class AccommodationReviewController {

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private final ReviewReportRepository reviewReportRepository;

    @Autowired
    public AccommodationReviewController(AccommodationService accommodationService, UserAccountService userAccountService,ReviewReportRepository reviewReportRepository ) {
        this.accommodationService = accommodationService;
        this.userAccountService = userAccountService;
        this.reviewReportRepository = reviewReportRepository;
    }

    @GetMapping(value = "/accommodations/{id}/accommodationReviews", name = "user gets all approved reviews for accommodation")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviews(@PathVariable Long id) {
        List<AccommodationReviewDTO> accommodationReviewsDTO = new ArrayList<>();
        accommodationReviewsDTO.add(new AccommodationReviewDTO());
        return new ResponseEntity<>(accommodationReviewsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/accommodations/accommodationReviews", name = "user adds a review for accommodation")
    public ResponseEntity<AccommodationReview> addAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        AccommodationReview savedReview = accommodationReviewService.saveAccommodationReview(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    //admin
    @GetMapping(value = "/accommodations/accommodationReviews/pending", name = "get all pending reviews for accommodations-admin")
    public ResponseEntity<List<AccommodationReview>> getPendingAccommodationReviews() {
        List<AccommodationReview> pendingReviews = accommodationReviewService.findAllPending() ;
        return new ResponseEntity<>(pendingReviews, HttpStatus.OK);
    }

    //ostali--> promentii na status approved
    @GetMapping(value = "/accommodations/{accommodationId}/accommodationReviews/pending", name = "get all approved  reviews for a specific accommodation")
    public ResponseEntity<List<AccommodationReview>>  getApprovedAccommodationReviews(@PathVariable Long accommodationId) {
        List<AccommodationReview> pendingReviews = accommodationReviewService.getApprovedAccommodationReviewsByAccommodation( accommodationId);
        return new ResponseEntity<>(pendingReviews, HttpStatus.OK);
    }

    @DeleteMapping("/accommodationReviews/{reviewId}")
    public ResponseEntity<Void> deleteAccommodationReviewById(@PathVariable Long reviewId) {
        Optional<AccommodationReview> reviewToDelete = Optional.ofNullable(accommodationReviewService.findById(reviewId));
         if (reviewToDelete.isPresent()) {

             reviewReportRepository.deleteByAccommodationReview_Id(reviewId);
            accommodationReviewService.deleteReviewById(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }

    @GetMapping(value = "/reviews/accommodation/requests", name = "admin gets all pending reviews for accommodations")
    public ResponseEntity<List<AccommodationReviewDTO>> getAllPendingAccommodationReviews() {
        List<AccommodationReview> accommodationReviews = accommodationReviewService.findAllPending();
        List<AccommodationReviewDTO> accommodationReviewDTOs = AccommodationReviewDTO.convertToDTO(accommodationReviews);
        return new ResponseEntity<>(accommodationReviewDTOs, HttpStatus.OK);
    }

    @PutMapping(value = "/reviews/admin/accommodation/{accommodationId}", name = "admin approves the review")
    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReviewById(@PathVariable Long accommodationId) {
        Optional<AccommodationReview> accommodationReviewToUpdate = accommodationReviewService.getReviewById(accommodationId);
        if (accommodationReviewToUpdate.isPresent()) {
            AccommodationReview updatedAccommodationReview = accommodationReviewToUpdate.get();
            updatedAccommodationReview.setStatus(ReviewStatus.APPROVED);
            accommodationReviewService.save(updatedAccommodationReview);

            accommodationReviewService.sendNotificationForAccommodationReview(updatedAccommodationReview);
            return new ResponseEntity<>(new AccommodationReviewDTO(updatedAccommodationReview), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/reviews/admin/accommodation/{accommodationId}", name = "admin rejects the review")
    public ResponseEntity<AccommodationReviewDTO> deleteAccommodationReviewByIdAdmin(@PathVariable Long accommodationId) {
        Optional<AccommodationReview> accommodationReviewToDelete = accommodationReviewService.getReviewById(accommodationId);
        if (accommodationReviewToDelete.isPresent()) {
            accommodationReviewService.deleteById(accommodationId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/accommodation/{accommodationId}/average-rating")
    public ResponseEntity<String> getAverageRatingByAccommodationId(@PathVariable Long accommodationId) {
        Double averageRating = accommodationReviewService.getAverageRatingByAccommodationId(accommodationId);

        if (averageRating != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedRating = decimalFormat.format(averageRating);

            return new ResponseEntity<>(formattedRating, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/accommodations/{id}/hasAcceptedReservation", name = "check if user has an accepted reservation in past")
    public ResponseEntity<Boolean> hasAcceptedReservationForAccommodation(@PathVariable Long id) {
        boolean hasAcceptedReservation = accommodationReviewService.isWithinSevenDaysFromEnd(id);
        return new ResponseEntity<>(hasAcceptedReservation, HttpStatus.OK);
    }

    @GetMapping("/accommodationReviews/{reviewId}")
    public ResponseEntity<AccommodationReviewDTO> getAccommodationReviewById(@PathVariable Long reviewId) {
        Optional<AccommodationReview> accommodationReview = accommodationReviewService.getReviewById(reviewId);
        return accommodationReview.map(review -> new ResponseEntity<>(new AccommodationReviewDTO(review), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
