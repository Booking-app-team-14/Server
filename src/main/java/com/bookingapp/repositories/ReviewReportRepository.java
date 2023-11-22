package com.bookingapp.repositories;

import com.bookingapp.entities.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {

    @Query("SELECT r FROM ReviewReport r WHERE r.status = 2")
    List<ReviewReport> findAllPending();
}
