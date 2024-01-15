package com.bookingapp.services;

import com.bookingapp.dtos.ReviewReportDTO;
import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.entities.ReviewReport;
import com.bookingapp.enums.ReportStatus;
import com.bookingapp.repositories.ReviewReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewReportService {

    @Autowired
    private ReviewReportRepository reviewReportRepository;

    @Autowired
    private AccommodationReviewService accommodationReviewService;

    public List<ReviewReport> findAllPending() {
        return reviewReportRepository.findAllPending();
    }

    public ReviewReport findById(Long id) {
        return reviewReportRepository.findById(id).orElse(null);
    }

    public void save(ReviewReport reviewReport) {
        reviewReportRepository.save(reviewReport);
    }


    public ReviewReport saveReviewReport(ReviewReportDTO reportDTO) {
        Optional<AccommodationReview> accommodationReviewOptional =
                Optional.ofNullable(accommodationReviewService.findById(reportDTO.getAccommodationReviewId()));

        if (accommodationReviewOptional.isPresent()) {
            AccommodationReview accommodationReview = accommodationReviewOptional.get();


            ReviewReport reviewReport = new ReviewReport();
            reviewReport.setAccommodationReview(accommodationReview);
            reviewReport.setReason(reportDTO.getReason());
            reviewReport.setStatus(ReportStatus.PENDING);
            reviewReport.setSentAt(LocalDateTime.now());


            return reviewReportRepository.save(reviewReport);
        } else {

            throw new EntityNotFoundException("AccommodationReview with ID " + reportDTO.getAccommodationReviewId() + " not found.");
        }
    }
}
