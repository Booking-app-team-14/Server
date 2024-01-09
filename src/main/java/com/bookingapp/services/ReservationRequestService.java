package com.bookingapp.services;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Availability;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.entities.UserAccount;
import com.bookingapp.repositories.ReservationRequestIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public  class ReservationRequestService {
    @Autowired
    private  UserAccountService userAccountService;

    @Autowired
    private ReservationRequestIRepository requestRepository;

    @Autowired
    AccommodationService accommodationService;

    public void createRequest(ReservationRequest reservation) {
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        LocalDate today = LocalDate.now();
        Optional<Accommodation> ac1= accommodationService.getAccommodationById(reservation.getAccommodationId());


        // Check-out date should be after the check-in date
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            throw new IllegalArgumentException("Check-out date should be after the check-in date");
        }

        // Minimum reservation duration is one night
        if (ChronoUnit.DAYS.between(startDate, endDate) < 1) {
            throw new IllegalArgumentException("Minimum reservation duration is one night");
        }

        // Check-in date should be at least one day after today
        if (startDate.isBefore(today.plusDays(1))) {
            throw new IllegalArgumentException("Check-in date should be at least one day after today");
        }

        // Additional validations based on reservation requirements
        if (reservation.getNumberOfGuests() < ac1.get().getMinNumberOfGuests() || reservation.getNumberOfGuests() > ac1.get().getMaxNumberOfGuests()) {
            throw new IllegalArgumentException("Guest count should be at least 1");
        }
        if(ac1.isPresent()) {
            // Check availability of accommodations between chosen dates
            Accommodation accommodation = ac1.get();
            Set<Availability> availabilities = accommodation.getAvailability();
            List<ReservationRequest> existingReservations = requestRepository.findRequestsBetweenDatesForAccommodationId(startDate, endDate, accommodation.getId());

            if (!existingReservations.isEmpty()) {
                boolean isReserved = existingReservations.stream().anyMatch(existingRequest -> {
                    boolean startDateOverlaps =
                            (startDate.isBefore(existingRequest.getEndDate()) && startDate.isAfter(existingRequest.getStartDate())) ||
                                    startDate.isEqual(existingRequest.getStartDate()) || startDate.isEqual(existingRequest.getEndDate());

                    boolean endDateOverlaps =
                            (endDate.isAfter(existingRequest.getStartDate()) && endDate.isBefore(existingRequest.getEndDate())) ||
                                    endDate.isEqual(existingRequest.getStartDate()) || endDate.isEqual(existingRequest.getEndDate());

                    boolean newRequestCoversExisting =
                            startDate.isBefore(existingRequest.getStartDate()) && endDate.isAfter(existingRequest.getEndDate());

                    return startDateOverlaps || endDateOverlaps || newRequestCoversExisting;
                });

                if (isReserved) {
                    throw new IllegalArgumentException("Accommodation is not available for the chosen dates");
                }
            }

            requestRepository.save(reservation);
        }
        else {
            throw new IllegalArgumentException("Accommodation does not exist");
        }
    }
//
//
//    public Request updateRequest(Long requestId, Request updatedRequest) {
//        Optional<Request> requestOptional = requestRepository.findById(requestId);
//        if (requestOptional.isPresent()) {
//            Request existingRequest = requestOptional.get();
//            return requestRepository.save(existingRequest);
//        }
//        return null;
//    }
//
//    public boolean deleteRequest(Long requestId) {
//
//        if (requestRepository.existsById(requestId)) {
//            requestRepository.deleteById(requestId);
//            return true;
//        }
//        return false;
//    }
//
//    public Request getGuestHistory(Long userId) {
//        Optional<Request> requestOptional = requestRepository.findById(userId);
//        if (requestOptional.isPresent()) {
//            Request existingRequest = requestOptional.get();
//            return  requestOptional.orElse(null);
//        }
//        return null;
//    }
//    public List<Request> findAllRequestsByUsername(Long guestId) {
//    return null;
////        List<Request> reservations = (List<Request>) requestRepository.findAllByRequestId(guestId);
////
////        List<GuestReservationDTO> historyReservations = new ArrayList<>();
////        for (Request reservation : reservations) {
////            GuestReservationDTO dto = new GuestReservationDTO(reservation.getUserId(), reservation.getUserId(),
////                     reservation.getStartDate(), reservation.getEndDate());
////
////            historyReservations.add(dto);
////
////        }
////        return null;
//    }


    public List<ReservationRequest> findById(Long id) {
        return requestRepository.findAllByUserId(id);
    }

    public List<ReservationRequest> findByUsername(String username) {
        return  requestRepository.findByUsername(username);
    }
}
