package com.bookingapp.controllers;
import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.AccommodationReportDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationReport;
import com.bookingapp.services.AccommodationReportService;
import com.bookingapp.services.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/api/accommodation-reports")
public class AccommodationReportController {

    @Autowired
    //private AccommodationService accommodationService;
    private AccommodationReportService accommodationreportService;


    /*@GetMapping
    public ResponseEntity<List<AccommodationReportDTO>> getAllAccommodationReports(){
        List<AccommodationReport> userReports = accommodationreportService.findAll();

        List<AccommodationReportDTO> accDTO = new ArrayList<>();
        for (AccommodationReport r : userReports) {
            accDTO.add(new AccommodationReportDTO(r));
        }

        return new ResponseEntity<>(accDTO, HttpStatus.OK);
    }*/

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationReportDTO> getAccommodationReport(@PathVariable Long id) {

        return new ResponseEntity<>(new AccommodationReportDTO(), HttpStatus.OK);

    }


    @PostMapping(value =  "/owners/{ownerId}"/*, consumes = "application/json"*/, name = "owner adds an accommodation report")
    public ResponseEntity<AccommodationReportDTO> addAccommodationReport(@PathVariable Long ownerId) {//, @RequestBody OwnerReviewDTO ownerReviewDTO) {

        return new ResponseEntity<>(new AccommodationReportDTO(), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAccommodationReport(@PathVariable Long id) {

        return new ResponseEntity<>("Deleted AccommodationReport", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateAccommodationReport(@PathVariable Long id) {

        return new ResponseEntity<>("Accommodation Report added", HttpStatus.OK);

    }



}
