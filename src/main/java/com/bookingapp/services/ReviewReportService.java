package com.bookingapp.services;

import com.bookingapp.entities.ReviewReport;
import com.bookingapp.repositories.ReviewReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewReportService {

    @Autowired
    private ReviewReportRepository reviewReportRepository;

    public List<ReviewReport> findAllPending() {
        return reviewReportRepository.findAllPending();
    }

    public ReviewReport findById(Long id) {
        return reviewReportRepository.findById(id).orElse(null);
    }

    public void save(ReviewReport reviewReport) {
        reviewReportRepository.save(reviewReport);
    }

}
