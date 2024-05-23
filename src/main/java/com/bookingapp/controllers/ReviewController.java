package com.bookingapp.controllers;

import com.bookingapp.dtos.ReviewDTO;
import com.bookingapp.entities.Review;
import com.bookingapp.repositories.OwnerReviewReportRepository;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @Autowired
    private final OwnerReviewReportRepository ownerReviewReportRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, OwnerReviewReportRepository ownerReviewReportRepository) {
        this.reviewService = reviewService;
        this.ownerReviewReportRepository = ownerReviewReportRepository;
    }

    @PostMapping
    public ResponseEntity<Review> saveReview(@RequestBody ReviewDTO reviewDTO) {
        Review savedReview = reviewService.saveReview(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    ///api/reviews/owner/{ownerId}
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Review>> getAllReviewsByOwnerId(@PathVariable Long ownerId) {
        List<Review> reviews = reviewService.getAllReviewsByOwnerId(ownerId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    ///api/reviews/{reviewId}
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable Long reviewId) {
        Optional<Review> reviewToDelete = reviewService.getReviewById(reviewId);
        if (reviewToDelete.isPresent()) {

            ownerReviewReportRepository.deleteByReviewId(reviewId);

            reviewService.deleteReviewById(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ///api/reviews/owner/{ownerId}/average-rating
    @GetMapping("/owner/{ownerId}/average-rating")
    public ResponseEntity<String> getAverageRatingByOwnerId(@PathVariable Long ownerId) {
        Double averageRating = reviewService.getAverageRatingByOwnerId(ownerId);

        if (averageRating != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedRating = decimalFormat.format(averageRating);

            return new ResponseEntity<>(formattedRating, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/owner/requests", name = "admin gets all pending reviews for owners")
    public ResponseEntity<List<ReviewDTO>> getAllPendingOwnerReviews() {
        List<Review> reviews = reviewService.getAllPendingOwnerReviews();
        List<ReviewDTO> reviewDTOs = ReviewDTO.convertToDTO(reviews);
        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/{reviewId}", name = "admin accepts the review")
    public ResponseEntity<Review> updateReviewById(@PathVariable Long reviewId) {
        Optional<Review> reviewToUpdate = reviewService.getReviewById(reviewId);
        if (reviewToUpdate.isPresent()) {
            Review updatedReview = reviewToUpdate.get();
            updatedReview.setApproved(true);
            reviewService.save(updatedReview);

            reviewService.sendNotificationForOwnerReview(updatedReview);
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/admin/{reviewId}", name = "admin rejects the review")
    public ResponseEntity<Review> deleteReviewByIdAdmin(@PathVariable Long reviewId) {
        Optional<Review> reviewToDelete = reviewService.getReviewById(reviewId);
        if (reviewToDelete.isPresent()) {
            reviewService.delete(reviewToDelete.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    ///api/reviews/report/{reviewId}

    @PutMapping("/report/{reviewId}")
    public ResponseEntity<Review> reportReview(@PathVariable Long reviewId) {
        Optional<Review> reviewToReport = reviewService.getReviewById(reviewId);

        if (reviewToReport.isPresent()) {
            Review reportedReview = reviewToReport.get();
            reportedReview.setReported(true);

            reviewService.save(reportedReview);

            return new ResponseEntity<>(reportedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/hasAcceptedReservationForOwner/{ownerId}")
    public ResponseEntity<Boolean> hasAcceptedReservationForOwner(@PathVariable Long ownerId) {
        boolean hasAcceptedReservation = reviewService.hasAcceptedReservationForOwner(ownerId);
        return new ResponseEntity<>(hasAcceptedReservation, HttpStatus.OK);
    }

    @GetMapping(value = "/{reviewId}", name = "get review for accommodation")
    public ResponseEntity<ReviewDTO> getReviewForAccommodation(@PathVariable Long reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);

        if (review.isPresent()) {
            ReviewDTO reviewDTO = new ReviewDTO(review.get());
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
