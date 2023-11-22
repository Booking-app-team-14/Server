package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationReportDTO;
import com.bookingapp.services.AccommodationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/api/accommodation-reports")
public class AccommodationReportController {

    private final AccommodationReportService reportService;

    @Autowired
    public AccommodationReportController(AccommodationReportService reportService) {
        this.reportService = reportService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<AccommodationReportDTO> createReport(@RequestBody AccommodationReportDTO reportDTO) {
        AccommodationReportDTO createdReport = reportService.createReport(reportDTO);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<AccommodationReportDTO>> getAllReports() {
        List<AccommodationReportDTO> reports = reportService.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationReportDTO> getReportById(@PathVariable Long id) {
        Optional<AccommodationReportDTO> report = Optional.ofNullable(reportService.getReportById(id));
        return report.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<AccommodationReportDTO> updateReport(@PathVariable Long id, @RequestBody AccommodationReportDTO reportDTO) {
        Optional<AccommodationReportDTO> updatedReport = Optional.ofNullable(reportService.updateReport(id, reportDTO));
        return updatedReport.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportService.deleteReport(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
