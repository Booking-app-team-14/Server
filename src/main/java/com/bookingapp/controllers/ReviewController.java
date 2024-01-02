package com.bookingapp.controllers;

import com.bookingapp.dtos.ReviewDTO;
import com.bookingapp.entities.Review;
import com.bookingapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // delete

}
