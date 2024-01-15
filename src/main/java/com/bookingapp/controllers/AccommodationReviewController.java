package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationReviewDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.entities.Review;
import com.bookingapp.enums.ReviewStatus;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.AccommodationService;
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

   /* @GetMapping(value = "/accommodationReviews", name = "admin gets all pending reviews")
    public ResponseEntity<List<AccommodationReviewDTO>> getAccommodationReviews() {
        List<AccommodationReview> accommodationReviews = accommodationReviewService.findAllPending();

        List<AccommodationReviewDTO> accommodationReviewsDTO = new ArrayList<>();
        for (AccommodationReview r : accommodationReviews) {
            accommodationReviewsDTO.add(new AccommodationReviewDTO(r));
        }

        return new ResponseEntity<>(accommodationReviewsDTO, HttpStatus.OK);
    }*/

    @PutMapping(value = "/accommodationReviews/{id}", /*consumes = "text/plain",*/ name = "admin approves/rejects the review")
    public ResponseEntity<AccommodationReviewDTO> updateAccommodationReview(@PathVariable Long id){//, @RequestBody String status) {

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

    //@PostMapping(value = "/accommodations/{id}/accommodationReviews", /*consumes = "application/json",*/ name = "user adds a review for the accommodation")
    //public ResponseEntity<AccommodationReviewDTO> addAccommodationReview(@PathVariable Long id){//, @RequestBody AccommodationReviewDTO accommodationReviewDTO) {

        //return new ResponseEntity<>(new AccommodationReviewDTO(), HttpStatus.CREATED);

//        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
//
//        if (accommodation == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        AccommodationReview accommodationReview = new AccommodationReview();
//        accommodationReview.setAccommodation(new Accommodation());//accommodation.get());
//        accommodationReview.setUser(userAccountService.getUserById(accommodationReviewDTO.getUserId()));
//        accommodationReview.setComment(accommodationReviewDTO.getComment());
//        accommodationReview.setRating(accommodationReviewDTO.getRating());
//        accommodationReview.setStatus(ReviewStatus.PENDING);
//        accommodationReview.setSentAt(accommodationReviewDTO.getSentAt());
//
//        accommodationReviewService.save(accommodationReview);
//
//        return new ResponseEntity<>(new AccommodationReviewDTO(accommodationReview), HttpStatus.CREATED);
    //}

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

}
