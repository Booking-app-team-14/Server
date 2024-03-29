package com.bookingapp.repositories;

import com.bookingapp.dtos.AccommodationReportDTO;
import com.bookingapp.entities.AccommodationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationReportRepository extends JpaRepository<AccommodationReport, Long> {

}

