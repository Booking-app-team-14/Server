package com.bookingapp.services;

import com.bookingapp.dtos.OwnerReviewReportDTO;
import com.bookingapp.dtos.ReviewReportDTO;
import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.entities.OwnerReviewReport;
import com.bookingapp.entities.Review;
import com.bookingapp.entities.ReviewReport;
import com.bookingapp.enums.ReportStatus;
import com.bookingapp.repositories.OwnerReviewReportRepository;
import com.bookingapp.repositories.ReviewReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class OwnerReviewReportService {

    @Autowired
    private OwnerReviewReportRepository reviewReportRepository;

    @Autowired
    private  ReviewService reviewService;

    public List<OwnerReviewReport> findAllPending() {
        return reviewReportRepository.findAllPending();
    }

    public OwnerReviewReport findById(Long id) {
        return reviewReportRepository.findById(id).orElse(null);
    }

    public void save(OwnerReviewReport reviewReport) {
        reviewReportRepository.save(reviewReport);
    }


    public OwnerReviewReport saveReviewReport(OwnerReviewReportDTO reportDTO) {
        Optional<Review> reviewOptional =
                Optional.ofNullable(reviewService.findById(reportDTO.getReviewId()));

        if (reviewOptional.isPresent()) {
             Review review = reviewOptional.get();


            OwnerReviewReport reviewReport = new OwnerReviewReport();
            reviewReport.setReview(review);
            if (review.getComment() != null) {
                reviewReport.setReason(reportDTO.getReason());
            } else {

                reviewReport.setReason(null);
            }
            reviewReport.setStatus(ReportStatus.PENDING);
            reviewReport.setSentAt(LocalDateTime.now());


            return reviewReportRepository.save(reviewReport);
        } else {

            throw new EntityNotFoundException("OwnerReview with ID " + reportDTO.getReviewId() + " not found.");
        }
    }

    public List<OwnerReviewReport> findAll() {
        return reviewReportRepository.findAll();
    }

    public void deleteById(Long id) {
        reviewReportRepository.deleteById(id);
    }


    public boolean isReviewReported(Long reviewId) {
        return reviewReportRepository.existsByReviewId(reviewId);
    }
}