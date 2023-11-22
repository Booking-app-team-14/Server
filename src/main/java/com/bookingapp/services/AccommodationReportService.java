package com.bookingapp.services;

import com.bookingapp.dtos.AccommodationReportDTO;
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

    public List<AccommodationReportDTO> getAllReports() {

        return reportRepository.findAll();
    }

    public AccommodationReportDTO getReportById(Long id) {

        return reportRepository.findById(id).orElse(null);
    }

    public AccommodationReportDTO createReport(AccommodationReportDTO reportDTO) {

        return reportRepository.save(reportDTO);
    }

    public AccommodationReportDTO updateReport(Long id, AccommodationReportDTO reportDTO) {

        if (reportRepository.existsById(id)) {
            reportDTO.setId(id);
            return reportRepository.save(reportDTO);
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

