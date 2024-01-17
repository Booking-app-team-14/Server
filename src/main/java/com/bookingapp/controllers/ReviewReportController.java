package com.bookingapp.controllers;

import com.bookingapp.dtos.ReviewReportDTO;
import com.bookingapp.entities.ReviewReport;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.enums.ReportStatus;
import com.bookingapp.services.AccommodationReviewService;
import com.bookingapp.services.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reviewReports")
public class ReviewReportController {

     @Autowired
    private ReviewReportService reviewReportService;

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    @GetMapping(name = "admin gets all pending review reports")
    public ResponseEntity<List<ReviewReportDTO>> getReviewReports() {
        List<ReviewReport> reviewReports = reviewReportService.findAllPending();

        List<ReviewReportDTO> reviewReportsDTO = new ArrayList<>();
        for (ReviewReport r : reviewReports) {
            reviewReportsDTO.add(new ReviewReportDTO(r));
        }

        return new ResponseEntity<>(reviewReportsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", /*consumes = "text/plain",*/ name = "admin approves/rejects the review report")
    public ResponseEntity<ReviewReportDTO> approveReviewReport(Long id){//, String status) {

        return new ResponseEntity<>(new ReviewReportDTO(), HttpStatus.OK);

//        ReviewReport reviewReport = reviewReportService.findById(id);
//
//        if (reviewReport == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        switch (status) {
//            case "ACCEPTED" -> reviewReport.setStatus(ReportStatus.ACCEPTED);
//            case "DECLINED" -> reviewReport.setStatus(ReportStatus.DECLINED);
//            default -> {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        return new ResponseEntity<>(new ReviewReportDTO(reviewReport), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", /*consumes = "application/json",*/ name = "owner reports a review")
    public ResponseEntity<ReviewReportDTO> createReviewReport(@PathVariable Long id){//, @RequestBody ReviewReportDTO reviewReportDTO) {

        return new ResponseEntity<>(new ReviewReportDTO(), HttpStatus.CREATED);

//        ReviewReport reviewReport = new ReviewReport();
//        reviewReport.setId(id);
//        reviewReport.setAccommodationReview(accommodationReviewService.findById(reviewReportDTO.getAccommodationReviewId()));
//        reviewReport.setReason(reviewReportDTO.getReason());
//        reviewReport.setStatus(ReportStatus.PENDING);
//        reviewReport.setSentAt(reviewReportDTO.getSentAt());
//
//        reviewReportService.save(reviewReport);
//
//        return new ResponseEntity<>(new ReviewReportDTO(reviewReport), HttpStatus.CREATED);
    }
//api/reviewReports/accommodationReviews/report
    @PostMapping("/accommodationReviews/report")
    public ResponseEntity<ReviewReport> reportAccommodationReview(@RequestBody ReviewReportDTO reportDTO) {

        ReviewReport savedReport = reviewReportService.saveReviewReport(reportDTO);

        if (savedReport != null) {
            return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reviews/isReported/{reviewId}")
    public ResponseEntity<Boolean> isReviewReported(@PathVariable Long reviewId) {
        boolean isReported = reviewReportService.isReviewReported(reviewId);
        return new ResponseEntity<>(isReported, HttpStatus.OK);
    }



}
