package com.bookingapp.services;

import com.bookingapp.dtos.MonthlyReportDTO;
import com.bookingapp.entities.MonthlyReport;
import com.bookingapp.repositories.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyReportService {

//    private final MonthlyReportRepository repository;
//
//    @Autowired
//    public MonthlyReportService(MonthlyReportRepository repository) {
//        this.repository = repository;
//    }
//
//    public List<MonthlyReport> getAllReports() {
//        return repository.findAll();
//    }
//
//    public Optional<MonthlyReport> getReportById(Long id) {
//
//        return repository.findById(id);
//    }
//
//    public MonthlyReport createReport(MonthlyReport report) {
//
//        return repository.save(report);
//    }
//
//    public Optional<MonthlyReport> updateReport(Long id, MonthlyReport report) {
//        if (repository.existsById(id)) {
//            report.setId(id);
//            return Optional.of(repository.save(report));
//        } else {
//            return Optional.empty();
//        }
//    }
//
//    public void deleteReport(Long id) {
//       repository.deleteById(id);
//    }
}

