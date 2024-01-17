package com.bookingapp.repositories;

import com.bookingapp.entities.OwnerReviewReport;
import com.bookingapp.entities.ReviewReport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerReviewReportRepository extends JpaRepository<OwnerReviewReport, Long> {

    @Query("SELECT r FROM OwnerReviewReport r WHERE r.status = 2")
    List<OwnerReviewReport> findAllPending();

    boolean existsByReviewId(Long reviewId);

    @Transactional
    void deleteByReviewId(Long reviewId);
}
