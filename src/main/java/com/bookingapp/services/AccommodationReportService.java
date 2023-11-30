package com.bookingapp.services;

import com.bookingapp.dtos.AccommodationReportDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationReport;
import com.bookingapp.repositories.AccommodationReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationReportService {

    private final AccommodationReportRepository reportRepository;

    @Autowired
    public AccommodationReportService(AccommodationReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<AccommodationReport> findAll() {
        return reportRepository.findAll();
    }

    public List<AccommodationReport> getAllReports() {

        return reportRepository.findAll();
    }

    public AccommodationReport getReportById(Long id) {

       return reportRepository.findById(id).orElse(null);
    }

    public AccommodationReport createReport(AccommodationReport report) {

        return reportRepository.save(report);
    }

    public AccommodationReport updateReport(Long id, AccommodationReport report) {

        if (reportRepository.existsById(id)) {
            report.setId(id);
            return reportRepository.save(report);
        } else {
            return null;
        }
    }
    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }
}

