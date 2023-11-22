package com.bookingapp.controllers;

import com.bookingapp.dtos.MonthlyReportDTO;
import com.bookingapp.services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthly-reports")
public class MonthlyReportController {

    private final MonthlyReportService reportService;

    @Autowired
    public MonthlyReportController(MonthlyReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<MonthlyReportDTO>> getAllReports() {
        List<MonthlyReportDTO> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyReportDTO> getReportById(@PathVariable Long id) {
        Optional<MonthlyReportDTO> report = reportService.getReportById(id);
        return report.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<MonthlyReportDTO> createReport(@RequestBody MonthlyReportDTO reportDTO) {
        MonthlyReportDTO createdReport = reportService.createReport(reportDTO);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyReportDTO> updateReport(@PathVariable Long id, @RequestBody MonthlyReportDTO reportDTO) {
        Optional<MonthlyReportDTO> updatedReport = reportService.updateReport(id, reportDTO);
        return updatedReport.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}

