package com.bookingapp.controllers;

import com.bookingapp.dtos.UserReportDTO;
import com.bookingapp.entities.Guest;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.entities.UserReport;
import com.bookingapp.enums.Role;
import com.bookingapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/userReports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getUserReports(){
            List<UserReport> userReports = userReportService.findAll();

            List<UserReportDTO> userReportsDTO = new ArrayList<>();
            for (UserReport r : userReports) {
                userReportsDTO.add(new UserReportDTO(r));
            }

            return new ResponseEntity<>(userReportsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserReportDTO> getUserReport(@PathVariable Long id) {

        return new ResponseEntity<>(new UserReportDTO(), HttpStatus.OK);

//        UserReport userReport = userReportService.findOne(id);
//
//        if (userReport == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(new UserReportDTO(userReport), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateReportedUser(@PathVariable Long id) {

        return new ResponseEntity<>("User Blocked", HttpStatus.OK);

//        UserReport userReport = userReportService.findOne(id);
//
//        if (userReport == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        UserAccount reportedUser = userReport.getReportedUser();
//        reportedUser.setBlocked(true);
//        userAccountService.save(reportedUser);
//
//        deleteUserReport(id);
//        return new ResponseEntity<>("User Blocked", HttpStatus.OK);
    }


    ///api/userReports/report
    @PostMapping("/report")
    public ResponseEntity<UserReport> saveUserReport(@RequestBody UserReportDTO userReportDTO) {
        UserReport userReport = userReportService.submitUserReport(userReportDTO);
        return new ResponseEntity<>(userReport, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserReport(@PathVariable Long id) {
        userReportService.deleteUserReport(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping(value = "/block-user/{reportId}", name = "admin blocks a user")
    public ResponseEntity<String> blockUser(@PathVariable Long reportId) {
        UserReport userReport = userReportService.findOne(reportId);
        UserAccount reportedUser = userReport.getReportedUser();
        reportedUser.setBlocked(true);

        if (reportedUser.getRole() == Role.GUEST) {
            reservationRequestService.cancelAllReservationsForGuest(reportedUser.getId());
        }
        else if (reportedUser.getRole() == Role.OWNER) {
            reservationRequestService.cancelAllReservationsForOwner(reportedUser.getUsername());
            accommodationService.setApprovedToFalseForAllOwnersApartments(reportedUser.getId());
        }

        userAccountService.save(reportedUser);
        return new ResponseEntity<>("User Blocked", HttpStatus.OK);
    }

}
