package com.bookingapp.repositories;

import com.bookingapp.dtos.MonthlyReportDTO;
import com.bookingapp.entities.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {

}

