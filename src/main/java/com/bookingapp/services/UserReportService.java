package com.bookingapp.services;

import com.bookingapp.dtos.UserReportDTO;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.entities.UserReport;
import com.bookingapp.repositories.UserReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserReportService {

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private UserAccountService userAccountService;

    //@Autowired
    //private ReservationService reservationService;

    public UserReport submitUserReport(UserReportDTO userReportDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserAccount) {
            UserAccount reportingUser = (UserAccount) authentication.getPrincipal();
            UserAccount reportedUser = userAccountService.getUserById(userReportDTO.getReportedUserId());

            // provera da li postoji prethodna rezervacija gosta za smestaj tog ownera
            // if (reservationService.hasPreviousReservation(reportingUser, reportedUser)) {
            /*UserReport userReport = new UserReport();
            userReport.setReportingUser(reportingUser);
            userReport.setReportedUser(reportedUser);
            userReport.setDescription(userReportDTO.getDescription());
            userReport.setSentAt(LocalDateTime.now());
            userReportRepository.save(userReport);*/

            //Review review = new Review(reviewDTO.getComment(), reviewDTO.getRating(), sender, recipient);
            //            return reviewRepository.save(review);

            UserReport userReport=new UserReport(reportingUser, reportedUser, userReportDTO.getDescription() );
            return userReportRepository.save(userReport);
            // } else {
            //     throw new IllegalArgumentException("Invalid report. No previous reservation.");
            // }
        //} else {
            //throw new IllegalStateException("User not authenticated or invalid principal.");
        //}
        //return null;
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

    public void deleteUserReport(Long id) {
        userReportRepository.deleteById(id);
    }

}
