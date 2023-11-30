package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationReportDTO;
import com.bookingapp.dtos.MonthlyReportDTO;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/monthly-reports")
public class MonthlyReportController {

    @Autowired
    //private AccommodationService accommodationService;
    private MonthlyReportService monthlyreportService;

    /*@GetMapping(value="api/monthly-reports", name="get all monthly reports")
    public ResponseEntity<List<MonthlyReportDTO>> getAllMonthlyReports() {

        List<MonthlyReportDTO> monthlyreportsDTO = new ArrayList<>();

        return new ResponseEntity<>(monthlyreportsDTO, HttpStatus.OK);

    }*/

    @GetMapping(value = "/{id}")
    public ResponseEntity<MonthlyReportDTO> getMonthlyReport(@PathVariable Long id) {

        return new ResponseEntity<>(new MonthlyReportDTO(), HttpStatus.OK);

    }


    @PostMapping(value =  "/owners/{ownerId}"/*, consumes = "application/json"*/, name = "owner adds an monthly report")
    public ResponseEntity<MonthlyReportDTO> addMonthlyReport(@PathVariable Long ownerId) {//, @RequestBody OwnerReviewDTO ownerReviewDTO) {

        return new ResponseEntity<>(new MonthlyReportDTO(), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMonthlyReport(@PathVariable Long id) {

        return new ResponseEntity<>("Deleted MonthlyReport", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateMonthlyReport(@PathVariable Long id) {

        return new ResponseEntity<>("Monthly Report added", HttpStatus.OK);

    }



}

