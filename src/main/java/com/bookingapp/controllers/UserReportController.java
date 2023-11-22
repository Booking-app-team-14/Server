package com.bookingapp.controllers;

import com.bookingapp.dtos.UserReportDTO;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.entities.UserReport;
import com.bookingapp.services.UserReportService;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/userReports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private UserAccountService userAccountService;

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
        UserReport userReport = userReportService.findOne(id);

        if (userReport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserReportDTO(userReport), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUserReport(@PathVariable Long id) {
        UserReport userReport = userReportService.findOne(id);

        if (userReport != null) {
            userReportService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/reportedUser")
    public ResponseEntity<Void> updateReportedUser(@PathVariable Long id) {
        UserReport userReport = userReportService.findOne(id);

        if (userReport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserAccount reportedUser = userReport.getReportedUser();
        reportedUser.setBlocked(true);
        userAccountService.save(reportedUser);

        deleteUserReport(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> saveUserReport(@RequestBody UserReportDTO userReportDTO) {
        UserReport userReport = new UserReport();
        userReport.setReportingUser(userAccountService.getUserById(userReportDTO.getReportingUserId()));
        userReport.setReportedUser(userAccountService.getUserById(userReportDTO.getReportedUserId()));
        userReport.setDescription(userReportDTO.getDescription());
        userReport.setSentAt(userReportDTO.getSentAt());

        userReport = userReportService.save(userReport);
        return new ResponseEntity<>(new UserReportDTO(userReport), HttpStatus.CREATED);
    }

}
