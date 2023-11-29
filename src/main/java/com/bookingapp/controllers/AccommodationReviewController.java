package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationReviewDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.enums.ReviewStatus;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api")
public class AccommodationReviewController {

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping(value = "/accommodations/{id}/accommodationReviews", name = "user gets all approved reviews for accommodation")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviews(@PathVariable Long id) {

        List<AccommodationReviewDTO> accommodationReviewsDTO = new ArrayList<>();
        accommodationReviewsDTO.add(new AccommodationReviewDTO());
        return new ResponseEntity<>(accommodationReviewsDTO, HttpStatus.OK);

//        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
//
//        if (accommodation == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        List<AccommodationReview> accommodationReviews = accommodationReviewService.findAllApprovedByAccommodationId(id);
//
//        List<AccommodationReviewDTO> accommodationReviewsDTO = new ArrayList<>();
//        for (AccommodationReview r : accommodationReviews) {
//            accommodationReviewsDTO.add(new AccommodationReviewDTO(r));
//        }
//
//        return new ResponseEntity<>(accommodationReviewsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodationReviews", name = "admin gets all pending reviews")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviews() {
        List<AccommodationReview> accommodationReviews = accommodationReviewService.findAllPending();

        List<AccommodationReviewDTO> accommodationReviewsDTO = new ArrayList<>();
        for (AccommodationReview r : accommodationReviews) {
            accommodationReviewsDTO.add(new AccommodationReviewDTO(r));
        }

        return new ResponseEntity<>(accommodationReviewsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/accommodationReviews/{id}", consumes = "text/plain", name = "admin approves/rejects the review")
    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReview(@PathVariable Long id, @RequestBody String status) {

        return new ResponseEntity<>(new AccommodationReviewDTO(), HttpStatus.OK);

//        AccommodationReview accommodationReview = accommodationReviewService.findById(id);
//
//        if (accommodationReview == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        switch (status) {
//            case "APPROVED" -> accommodationReview.setStatus(ReviewStatus.APPROVED);
//            case "REJECTED" -> accommodationReview.setStatus(ReviewStatus.REJECTED);
//            default -> {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        accommodationReviewService.save(accommodationReview);
//
//        return new ResponseEntity<>(new AccommodationReviewDTO(accommodationReview), HttpStatus.OK);
    }

    @PostMapping(value = "/accommodations/{id}/accommodationReviews", consumes = "application/json", name = "user adds a review for the accommodation")
    public ResponseEntity<AccommodationReviewDTO> addAccommodationReview(@PathVariable Long id, @RequestBody AccommodationReviewDTO accommodationReviewDTO) {
//        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        Accommodation accommodation = new Accommodation();
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationReview accommodationReview = new AccommodationReview();
        accommodationReview.setAccommodation(new Accommodation());//accommodation.get());
        accommodationReview.setUser(userAccountService.getUserById(accommodationReviewDTO.getUserId()));
        accommodationReview.setComment(accommodationReviewDTO.getComment());
        accommodationReview.setRating(accommodationReviewDTO.getRating());
        accommodationReview.setStatus(ReviewStatus.PENDING);
        accommodationReview.setSentAt(accommodationReviewDTO.getSentAt());

        accommodationReviewService.save(accommodationReview);

        return new ResponseEntity<>(new AccommodationReviewDTO(accommodationReview), HttpStatus.CREATED);
    }


}
