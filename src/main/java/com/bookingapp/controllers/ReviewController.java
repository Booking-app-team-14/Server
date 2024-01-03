package com.bookingapp.controllers;

import com.bookingapp.dtos.ReviewDTO;
import com.bookingapp.entities.Review;
import com.bookingapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    @Autowired
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
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
            reviewService.deleteReviewById(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
