package com.bookingapp.repositories;

import com.bookingapp.entities.ReviewReport;
import com.bookingapp.enums.ReportStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {

    @Query("SELECT r FROM ReviewReport r WHERE r.status = 2")
    List<ReviewReport> findAllPending();

    boolean existsByAccommodationReviewIdAndStatus(Long reviewId, ReportStatus status);

    @Transactional
    void deleteByAccommodationReview_Id(Long reviewId);
}
