package com.bookingapp.services;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.entities.*;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.repositories.ReservationRequestIRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Service
public  class ReservationRequestService {

    @Autowired
    private ReservationRequestIRepository requestRepository;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserAccountService userAccountService;

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

    public void sendNotificationForReservation(ReservationRequest request, NotificationType type) {
        Owner owner = (Owner) userAccountService.findByUsername(request.getUserUsername());
        for (NotificationType notWantedType : owner.getNotWantedNotificationTypes()){
            if (notWantedType.equals(type)){
                return;
            }
        }

        Guest guest = null;
        try {
            guest = (Guest)userAccountService.findById(request.getUserId());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        if (type.equals(NotificationType.RESERVATION_REQUEST_CREATED)) {
            NotificationReservationCreated notification = new NotificationReservationCreated();
            notification.setReservationRequestId(request.getId());
            notification.setSender(guest);
            notification.setReceiver(owner);
            notification.setSeen(false);
            notification.setSentAt(LocalDateTime.now());
            notification.setType(type);
            notificationService.saveReservationRequestCreated(notification);
        } else {
            NotificationReservationCancelled notification = new NotificationReservationCancelled();
            notification.setReservationRequestId(request.getId());
            notification.setSender(guest);
            notification.setReceiver(owner);
            notification.setSeen(false);
            notification.setSentAt(LocalDateTime.now());
            notification.setType(type);
            notificationService.saveReservationRequestCancelled(notification);
        }
//        notification.setSender(guest);
//        notification.setReceiver(owner);
//        notification.setSeen(false);
//        notification.setSentAt(LocalDateTime.now());
//        notification.setType(type);
//        notificationService.saveReservationRequestCreated(notification);

        notificationService.sendNotification(owner.getUsername());
    }

    public void sendNotificationForReservationForGuest(ReservationRequest reservationRequest, boolean approved) {
        Owner owner = (Owner) userAccountService.findByUsername(reservationRequest.getUserUsername());
        for (NotificationType notWantedType : owner.getNotWantedNotificationTypes()){
            if (notWantedType.equals(NotificationType.RESERVATION_REQUEST_RESPONSE)){
                return;
            }
        }

        Guest guest = null;
        try {
            guest = (Guest) userAccountService.findById(reservationRequest.getUserId());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        NotificationReservationRequestResponse notification = new NotificationReservationRequestResponse();
        notification.setReservationRequestId(reservationRequest.getId());
        notification.setSender(owner);
        notification.setReceiver(guest);
        notification.setApproved(approved);
        notification.setSeen(false);

        notification.setSentAt(LocalDateTime.now());
        notification.setType(NotificationType.RESERVATION_REQUEST_RESPONSE);
        notificationService.saveRequestResponse(notification);
        notificationService.sendNotification(guest.getUsername());
    }

    public ReservationRequest getReservationRequestById(Long reservationRequestId) {
        return requestRepository.findById(reservationRequestId).orElseThrow(() -> null);
    }

    public void rejectOverlappingReservationRequests(ReservationRequest reservationRequest) {
        List<ReservationRequest> requests = this.findByAccommodationId(reservationRequest.getAccommodationId());
        List<ReservationRequest> overlappingRequests = requests.stream()
                .filter(r -> r.getRequestStatus().equals(RequestStatus.SENT))
                .filter(r ->
                        (r.getStartDate().isBefore(reservationRequest.getEndDate()) && r.getEndDate().isAfter(reservationRequest.getStartDate())) ||
                                (r.getStartDate().isEqual(reservationRequest.getEndDate()) || r.getEndDate().isEqual(reservationRequest.getStartDate())) ||
                                (r.getStartDate().isBefore(reservationRequest.getStartDate()) && r.getEndDate().isAfter(reservationRequest.getEndDate())) ||
                                (r.getStartDate().isAfter(reservationRequest.getStartDate()) && (r.getEndDate().isAfter(reservationRequest.getEndDate()) && r.getStartDate().isBefore(reservationRequest.getEndDate()))) ||
                                (r.getStartDate().isBefore(reservationRequest.getStartDate()) && (r.getEndDate().isBefore(reservationRequest.getEndDate()) && r.getEndDate().isAfter(reservationRequest.getStartDate())))
                )
                .toList();

        for (ReservationRequest r : overlappingRequests) {
            r.setRequestStatus(RequestStatus.DECLINED);
            this.save(r);
        }
    }

    public boolean guestHasActiveRequests(Long id) {
        for (ReservationRequest rr : this.findByUserId(id)) {
            if (rr.getRequestStatus() == RequestStatus.ACCEPTED &&
                    (rr.getEndDate().isAfter(LocalDate.now()) || rr.getEndDate().isEqual(LocalDate.now()))) {
                return true;
            }
        }
        return false;
    }

    public boolean ownerHasActiveRequests(Owner owner) {
        for (ReservationRequest rr : owner.getReservations()){
            if (rr.getRequestStatus() == RequestStatus.ACCEPTED &&
                    (rr.getEndDate().isAfter(LocalDate.now()) || rr.getEndDate().isEqual(LocalDate.now()))) {
                return true;
            }
        }
        return false;
    }

}
