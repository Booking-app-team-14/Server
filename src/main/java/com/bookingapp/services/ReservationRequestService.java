package com.bookingapp.services;

import com.bookingapp.entities.*;
import com.bookingapp.enums.RequestStatus;
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

    @Autowired
    private ReservationService reservationService;

    public void createRequest(ReservationRequest reservation) {
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        LocalDate today = LocalDate.now();
        Optional<Accommodation> ac1= accommodationService.getAccommodationById(reservation.getAccommodationId());


        // Check-out date should be after the check-in date
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Check-out date should be after the check-in date");
        }

//        // Check-in date should be at least one day after today
//        if (startDate.isBefore(today.plusDays(1))) {
//            throw new IllegalArgumentException("Check-in date should be at least one day after today");
//        }

        // Additional validations based on reservation requirements
        if (reservation.getNumberOfGuests() < ac1.get().getMinNumberOfGuests() || reservation.getNumberOfGuests() > ac1.get().getMaxNumberOfGuests()) {
            throw new IllegalArgumentException("Accommodation guest range is not respected");
        }
        if(ac1.isPresent()) {

            Accommodation accommodation = ac1.get();
            Set<Availability> availabilities = accommodation.getAvailability();


            boolean isAvailable = availabilities.stream().anyMatch(availability -> {
                boolean startDateWithinRange = startDate.isEqual(availability.getStartDate()) || startDate.isAfter(availability.getStartDate());
                boolean endDateWithinRange = endDate.isEqual(availability.getEndDate()) || endDate.isBefore(availability.getEndDate());

                return startDateWithinRange && endDateWithinRange;
            });


            if (!isAvailable) {
                throw new IllegalArgumentException("Accommodation is not available for the chosen dates");
            }

//            List<ReservationRequest> existingReservations = requestRepository.findRequestsBetweenDatesForAccommodationId(startDate, endDate, accommodation.getId(), RequestStatus.ACCEPTED);
//
//            if (!existingReservations.isEmpty()) {
//                boolean isReserved = existingReservations.stream().anyMatch(existingRequest -> {
//                    boolean startDateOverlaps =
//                            (startDate.isBefore(existingRequest.getEndDate()) && startDate.isAfter(existingRequest.getStartDate())) ||
//                                    startDate.isEqual(existingRequest.getStartDate()) || startDate.isEqual(existingRequest.getEndDate());
//
//                    boolean endDateOverlaps =
//                            (endDate.isAfter(existingRequest.getStartDate()) && endDate.isBefore(existingRequest.getEndDate())) ||
//                                    endDate.isEqual(existingRequest.getStartDate()) || endDate.isEqual(existingRequest.getEndDate());
//
//                    boolean newRequestCoversExisting =
//                            startDate.isBefore(existingRequest.getStartDate()) && endDate.isAfter(existingRequest.getEndDate());
//
//                    return startDateOverlaps || endDateOverlaps || newRequestCoversExisting;
//                });
//
//                if (isReserved) {
//                    throw new IllegalArgumentException("Accommodation is not available for the chosen dates");
//                }
//            }

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


    public Optional<ReservationRequest> findById(Long id) {
        return requestRepository.findById(id);
    }

    public List<ReservationRequest> findByUsername(String username) {
        return  requestRepository.findByUsername(username);
    }
    public List<ReservationRequest> findAll(){
        return  requestRepository.findAll();
    }

    public void delete(ReservationRequest reservation) {
        requestRepository.delete(reservation);
    }

    public List<ReservationRequest> findByUserId(Long id) {
        return requestRepository.findAllByUserId(id);
    }

    public void save(ReservationRequest reservation) {
        requestRepository.save(reservation);
    }

    public List<ReservationRequest> findByAccommodationId(Long accommodationId) {
        return requestRepository.findAllByAccommodationId(accommodationId);
    }


    public List<ReservationRequest> findAllByAccommodationAndYear(Accommodation accommodation, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return requestRepository.findAllByAccommodationAndEndDateBetween(accommodation.getId(), startDate, endDate);
    }

    public void cancelAllReservationsForGuest(Long reportedUserId) {
        List<ReservationRequest> reservationsRequests = requestRepository.findAllByUserId(reportedUserId);

        for (ReservationRequest reservationRequest : reservationsRequests) {
            if (reservationRequest.getEndDate().isBefore(LocalDate.now()) ||
                    ((reservationRequest.getStartDate().isBefore(LocalDate.now()) || reservationRequest.getStartDate().isEqual(LocalDate.now()))
                    && (reservationRequest.getEndDate().isAfter(LocalDate.now())) || reservationRequest.getEndDate().isEqual(LocalDate.now())
                    || reservationRequest.getRequestStatus().equals(RequestStatus.DECLINED))
            ) {
                continue;
            }
            accommodationService.cancelReservation(reservationRequest);

            Accommodation accommodation = accommodationService.findById(reservationRequest.getAccommodationId()).get();
            Owner owner = (Owner) userAccountService.findByUsername(accommodation.getOwner().getUsername());
            owner.getReservations().remove(reservationRequest);
            userAccountService.save(owner);

            this.delete(reservationRequest);
            reservationService.delete(reservationRequest.getReservationId());
        }
    }

    public void cancelAllReservationsForOwner(String ownerUsername) {
        Owner owner = (Owner) userAccountService.findByUsername(ownerUsername);
        Set<ReservationRequest> reservationsRequests = ((Owner) userAccountService.findByUsername(ownerUsername)).getReservations();

        Set<ReservationRequest> reservationsRequestsCopy = new HashSet<>(reservationsRequests);

        for (ReservationRequest reservationRequest : reservationsRequestsCopy) {
            if (reservationRequest.getEndDate().isBefore(LocalDate.now()) ||
                    ((reservationRequest.getStartDate().isBefore(LocalDate.now()) || reservationRequest.getStartDate().isEqual(LocalDate.now()))
                            && (reservationRequest.getEndDate().isAfter(LocalDate.now())) || reservationRequest.getEndDate().isEqual(LocalDate.now()))
            ) {
                continue;
            }
            accommodationService.cancelReservation(reservationRequest);

            owner.getReservations().remove(reservationRequest);
            userAccountService.save(owner);

            this.delete(reservationRequest);
            reservationService.delete(reservationRequest.getReservationId());
        }


    }

}
