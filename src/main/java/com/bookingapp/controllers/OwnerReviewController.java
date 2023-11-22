package com.bookingapp.controllers;

import com.bookingapp.dtos.OwnerReviewDTO;
import com.bookingapp.entities.OwnerReview;
import com.bookingapp.enums.ReviewStatus;
import com.bookingapp.services.OwnerReviewService;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class OwnerReviewController {

    @Autowired
    private OwnerReviewService ownerReviewService;

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping(value = "/owners/{ownerId}/reviews", name = "user gets all approved reviews for owner")
    public ResponseEntity<List<OwnerReviewDTO>> getOwnerReviews(@PathVariable Long ownerId) {
        List<OwnerReview> ownerReviews = ownerReviewService.findAllApprovedByOwnerId(ownerId);

        List<OwnerReviewDTO> ownerReviewsDTO = new ArrayList<>();
        for (OwnerReview r : ownerReviews) {
            ownerReviewsDTO.add(new OwnerReviewDTO(r));
        }

        return new ResponseEntity<>(ownerReviewsDTO, HttpStatus.OK);
    }

    @PostMapping(value =  "/owners/{ownerId}/reviews", consumes = "application/json", name = "user adds a review for the owner")
    public ResponseEntity<OwnerReviewDTO> addOwnerReview(@PathVariable Long ownerId, @RequestBody OwnerReviewDTO ownerReviewDTO) {
        OwnerReview ownerReview = new OwnerReview();
        ownerReview.setUser(userAccountService.findById(ownerReviewDTO.getUserId()));
        ownerReview.setOwner(userAccountService.findById(ownerId));
        ownerReview.setRating(ownerReviewDTO.getRating());
        ownerReview.setComment(ownerReviewDTO.getComment());
        ownerReview.setSentAt(ownerReviewDTO.getSentAt());

        ownerReviewService.save(ownerReview);

        return new ResponseEntity<>(new OwnerReviewDTO(ownerReview), HttpStatus.CREATED);
    }

    @GetMapping(value = "/ownerReviews", name = "admin gets all pending reviews")
    public ResponseEntity<List<OwnerReviewDTO>> getOwnerReviews() {
        List<OwnerReview> ownerReviews = ownerReviewService.findAllPending();

        List<OwnerReviewDTO> ownerReviewsDTO = new ArrayList<>();
        for (OwnerReview r : ownerReviews) {
            ownerReviewsDTO.add(new OwnerReviewDTO(r));
        }

        return new ResponseEntity<>(ownerReviewsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/ownerReviews/{id}", consumes = "text/plain", name = "admin approves/rejects the review")
    public ResponseEntity<OwnerReviewDTO> updateOwnerReview(@PathVariable Long id, @RequestBody String status) {
        OwnerReview ownerReview = ownerReviewService.findById(id);

        if (ownerReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        switch (status) {
            case "APPROVED" -> ownerReview.setStatus(ReviewStatus.APPROVED);
            case "REJECTED" -> ownerReview.setStatus(ReviewStatus.REJECTED);
            default -> {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        ownerReviewService.save(ownerReview);

        return new ResponseEntity<>(new OwnerReviewDTO(ownerReview), HttpStatus.OK);
    }

}
