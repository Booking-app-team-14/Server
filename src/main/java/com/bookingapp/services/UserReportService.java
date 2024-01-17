package com.bookingapp.services;

import com.bookingapp.dtos.UserReportDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.entities.UserReport;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserReportService {

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ReviewService  reviewService;
    @Autowired
    private AccommodationRepository accommodationRepository;

    //@Autowired
    //private ReservationService reservationService;

    public UserReport submitUserReport(UserReportDTO userReportDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


            UserAccount reportingUser = (UserAccount) authentication.getPrincipal();
            UserAccount reportedUser = userAccountService.getUserById(userReportDTO.getReportedUserId());

        boolean hasReservation = reviewService.hasAcceptedReservation(reportingUser.getId() );

        //boolean hasReservationOwner = reviewService.hasAcceptedReservationForGuest(reportingUser.getId() );

        //if (!hasReservation  ) {
            //throw new IllegalArgumentException("Must have at least one accepted reservation in the past in  this accommodation to submit a report.");
        //}

        if (userReportRepository.existsByReportingUserAndReportedUser(reportingUser, reportedUser)) {

            throw new IllegalArgumentException("User already reported.");
        }

            UserReport userReport=new UserReport(reportingUser, reportedUser, userReportDTO.getDescription() );
            return userReportRepository.save(userReport);

    }




    public List<UserReport> findAll() {
        return userReportRepository.findAll();
    }

    public UserReport findOne(Long id) {
        return userReportRepository.findById(id).orElse(null);
    }

    public void remove(Long id) {
        userReportRepository.deleteById(id);
    }

    public UserReport save(UserReport userReport) {
        return userReportRepository.save(userReport);
    }

}
