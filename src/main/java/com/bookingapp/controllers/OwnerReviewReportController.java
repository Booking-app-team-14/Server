package com.bookingapp.controllers;

import com.bookingapp.dtos.OwnerReviewReportDTO;
import com.bookingapp.dtos.ReviewReportDTO;
import com.bookingapp.entities.OwnerReviewReport;
import com.bookingapp.entities.Review;
import com.bookingapp.entities.ReviewReport;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.OwnerReviewReportService;
import com.bookingapp.services.ReviewReportService;
import com.bookingapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/ownerReviewReports")
public class OwnerReviewReportController {

    @Autowired
    private OwnerReviewReportService reviewReportService;

    @Autowired
    private ReviewService reviewService;


    @PostMapping("/reviews/report")
    public ResponseEntity<OwnerReviewReport> reportOwnerReview(@RequestBody OwnerReviewReportDTO reportDTO) {

        OwnerReviewReport savedReport = reviewReportService.saveReviewReport(reportDTO);

        if (savedReport != null) {

            Optional<Review> reviewOptional = Optional.ofNullable(reviewService.findById(reportDTO.getReviewId()));

            if (reviewOptional.isPresent()) {
                Review review = reviewOptional.get();
                review.setReported(true);
                reviewService.save(review);
            }

            return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}


