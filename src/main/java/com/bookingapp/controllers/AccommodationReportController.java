package com.bookingapp.controllers;
import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.AccommodationReportDTO;
import com.bookingapp.dtos.MonthlyAccommodationReportDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.enums.Role;
import com.bookingapp.services.AccommodationReportService;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.ReservationRequestService;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/accommodation-reports")
public class AccommodationReportController {

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @GetMapping(value = "/{ownerId}")
    public ResponseEntity<List<AccommodationReportDTO>> getAllAccommodationReports(
            @PathVariable Long ownerId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        UserAccount userAccount = userAccountService.getUserById(ownerId);

        if (userAccount.getRole() != Role.OWNER) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Owner owner = (Owner) userAccount;

        List<Accommodation> accommodations = accommodationService.findAllByPricePerNightAsc().stream().filter(accommodation -> Objects.equals(accommodation.getOwner().getId(), ownerId)).toList();
        List<ReservationRequest> reservationRequests= reservationRequestService.findAll().stream().filter(reservationRequest -> reservationRequest.getUserUsername().equals(owner.getUsername())).toList();

        List<AccommodationReportDTO> accDTOList = new ArrayList<>();

        for (Accommodation accommodation : accommodations) {
            int numberOfReservations = getNumberOfReservations(accommodation, reservationRequests, startDate, endDate);
            double totalProfit = getTotalProfit(accommodation, reservationRequests, startDate, endDate);

            accDTOList.add(new AccommodationReportDTO(accommodation, numberOfReservations, totalProfit, accommodationService));
        }

        return new ResponseEntity<>(accDTOList, HttpStatus.OK);
    }

    private int getNumberOfReservations(Accommodation accommodation, List<ReservationRequest> reservationRequests, LocalDate startDate, LocalDate endDate) {
        return (int) reservationRequests.stream()
                .filter(request -> request.getAccommodationId().equals(accommodation.getId()))
                .filter(request -> request.getEndDate().isBefore(endDate) && request.getEndDate().isAfter(startDate))
                .filter(request -> request.getRequestStatus() == RequestStatus.ACCEPTED)
                .count();
    }

    private double getTotalProfit(Accommodation accommodation, List<ReservationRequest> reservationRequests, LocalDate startDate, LocalDate endDate) {
        return reservationRequests.stream()
                .filter(request -> request.getAccommodationId().equals(accommodation.getId()))
                .filter(request -> request.getEndDate().isBefore(endDate) && request.getEndDate().isAfter(startDate))
                .filter(request -> request.getRequestStatus() == RequestStatus.ACCEPTED)
                .mapToDouble(ReservationRequest::getTotalPrice)
                .sum();
    }


    @GetMapping(value = "/{ownerId}/{accommodationId}/monthly-report")
    public ResponseEntity<Map<String, MonthlyAccommodationReportDTO>> getMonthlyAccommodationReport(
            @PathVariable Long ownerId,
            @PathVariable Long accommodationId,
            @RequestParam int year) {

        UserAccount userAccount = userAccountService.getUserById(ownerId);

        if (userAccount.getRole() != Role.OWNER) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Owner owner = (Owner) userAccount;
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(accommodationId);

        if (accommodation == null || !accommodation.get().getOwner().equals(owner)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ReservationRequest> reservationRequests = reservationRequestService.findAllByAccommodationAndYear(accommodation.get(), year);

        Map<String, MonthlyAccommodationReportDTO> monthlyReportMap = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            int numberOfReservations = getNumberOfReservations(accommodation.get(), reservationRequests, startDate, endDate);
            double totalProfit = getTotalProfit(accommodation.get(), reservationRequests, startDate, endDate);

            MonthlyAccommodationReportDTO monthlyReportDTO = new MonthlyAccommodationReportDTO(startDate.getMonth().toString(), numberOfReservations, totalProfit);
            monthlyReportMap.put(startDate.getMonth().toString(), monthlyReportDTO);
        }

        return new ResponseEntity<>(monthlyReportMap, HttpStatus.OK);
    }



}
