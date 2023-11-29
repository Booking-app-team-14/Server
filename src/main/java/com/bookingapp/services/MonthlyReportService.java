package com.bookingapp.services;

import com.bookingapp.dtos.MonthlyReportDTO;
import com.bookingapp.repositories.MonthlyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyReportService {

//    private final MonthlyReportRepository repository;

//    @Autowired
//    public MonthlyReportService(MonthlyReportRepository repository) {
//        this.repository = repository;
//    }

    public List<MonthlyReportDTO> getAllReports() {
        //return repository.findAll();
        return null;
    }

    public Optional<MonthlyReportDTO> getReportById(Long id) {

//        return repository.findById(id);
        return null;
    }

    public MonthlyReportDTO createReport(MonthlyReportDTO reportDTO) {

        return null;
//        return repository.save(reportDTO);
    }

    public Optional<MonthlyReportDTO> updateReport(Long id, MonthlyReportDTO reportDTO) {
//        if (repository.existsById(id)) {
//            reportDTO.setId(id);
//            return Optional.of(repository.save(reportDTO));
//        } else {
//            return Optional.empty();
//        }
        return null;
    }

    public void deleteReport(Long id) {
        return;
//        repository.deleteById(id);
    }
}

