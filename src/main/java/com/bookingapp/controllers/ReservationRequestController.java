package com.bookingapp.controllers;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.entities.*;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationRequestController {

    @Autowired
    private ReservationRequestService requestService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/requests", name = "guest makes a reservation request")
    public ResponseEntity<Long> createReservationRequest(@RequestBody ReservationRequestDTO requestDTO) {
        ReservationRequest request = new ReservationRequest(requestDTO);
        try {
            Optional<Accommodation> acc = accommodationService.getAccommodationById(request.getAccommodationId());
            if (acc.isEmpty()) {
                throw new IllegalArgumentException("Accommodation not found");
            }
            requestService.createRequest(request);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<Accommodation> acc = accommodationService.getAccommodationById(request.getAccommodationId());
        if (acc.isPresent() && acc.get().isAutomatic()) {
            this.approveReservationRequest(request.getId());
        }

        return new ResponseEntity<>(request.getId(), HttpStatus.CREATED);
    }


    @GetMapping(value = "/requests/guest/{id}", name = "gets a requests by guest Id")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequestsByGuestId(@PathVariable Long id) {
        List<ReservationRequest> requests = requestService.findByUserId(id);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ReservationRequestDTO> result = new ArrayList<>();

        for (ReservationRequest request : requests) {
            result.add(new ReservationRequestDTO(request));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/owner/{username}", name = "gets a requests by owner username")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequestsByOwnerUsername(@PathVariable String username) {
        List<ReservationRequest> requests = requestService.findByUsername(username);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ReservationRequestDTO> result = new ArrayList<>();

        for (ReservationRequest request : requests) {
            result.add(new ReservationRequestDTO(request));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping(value = "/requests/{Id}", name = "updates the status of the request")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@PathVariable Long requestId/* @RequestBody RequestDTO updatedRequest*/) {
        return new ResponseEntity<>(new ReservationRequestDTO(), HttpStatus.OK);
    }

    @PutMapping(value = "/requests/reject/{id}", name = "owner rejects a reservation request")
    public ResponseEntity<String> rejectReservationRequest(@PathVariable Long id) {
        Optional<ReservationRequest> reservationOptional = requestService.findById(id);

        if (reservationOptional.isPresent()) {
            ReservationRequest reservationRequest = reservationOptional.get();

            reservationRequest.setRequestStatus(RequestStatus.DECLINED);
            requestService.save(reservationRequest);

            requestService.sendNotificationForReservationForGuest(reservationRequest, false);
        } else {
            return new ResponseEntity<>("Reservation request not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Reservation request rejected", HttpStatus.OK);
    }

    @PutMapping(value = "/requests/approve/{id}", name = "owner approves a reservation request")
    public ResponseEntity<ReservationRequestDTO> approveReservationRequest(@PathVariable Long id) {
        Optional<ReservationRequest> reservationOptional = requestService.findById(id);
        ReservationRequest reservationRequest = null;

        if (reservationOptional.isPresent()) {
            reservationRequest = reservationOptional.get();

            reservationRequest.setRequestStatus(RequestStatus.ACCEPTED);
            requestService.save(reservationRequest);

            requestService.rejectOverlappingReservationRequests(reservationRequest);

            Reservation reservation = accommodationService.reserveAvailability(reservationRequest.getAccommodationId(), reservationRequest.getStartDate(), reservationRequest.getEndDate());
            reservationService.save(reservation);
            reservationRequest.setReservationId(reservation.getId());
            requestService.save(reservationRequest);

            Accommodation accommodation = accommodationService.findById(reservationRequest.getAccommodationId()).get();
            Owner owner = (Owner) userAccountService.findByUsername(accommodation.getOwner().getUsername());
            owner.getReservations().add(reservationRequest);
            userAccountService.save(owner);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ReservationRequestDTO(reservationRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/requests/{id}")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable Long id) {
        Optional<ReservationRequest> reservationOptional = requestService.findById(id);

        if (reservationOptional.isPresent()) {
            ReservationRequest reservationRequest = reservationOptional.get();

            if (reservationRequest.getRequestStatus().equals(RequestStatus.SENT)) {
                requestService.sendNotificationForReservation(reservationRequest, NotificationType.RESERVATION_REQUEST_CANCELLED);

                requestService.delete(reservationRequest);
                userAccountService.increaseNumberOfCancellations(reservationRequest.getUserId());
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else if (reservationRequest.getRequestStatus().equals(RequestStatus.ACCEPTED)) {
                accommodationService.cancelReservation(reservationRequest);

                Accommodation accommodation = accommodationService.findById(reservationRequest.getAccommodationId()).get();
                Owner owner = (Owner) userAccountService.findByUsername(accommodation.getOwner().getUsername());
                owner.getReservations().remove(reservationRequest);
                userAccountService.save(owner);

                requestService.sendNotificationForReservation(reservationRequest, NotificationType.RESERVATION_REQUEST_CANCELLED);

                requestService.delete(reservationRequest);
                userAccountService.increaseNumberOfCancellations(reservationRequest.getUserId());
                reservationService.delete(reservationRequest.getReservationId());

                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Deletion not allowed for reservations with status other than SENT or ACCEPTED", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Reservation request not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/users/{Id}/requests", name = "user gets reservation history")
    public ResponseEntity<List<ReservationRequestDTO>> getAllGuestHistoryReservations(@PathVariable Long Id) {
        List<ReservationRequestDTO> reservations = new ArrayList<ReservationRequestDTO>();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/{id}", name = "gets a request by id")
    public ResponseEntity<ReservationRequestDTO> getReservationRequestById(@PathVariable Long id) {
        Optional<ReservationRequest> request = requestService.findById(id);
        if (request.isPresent()) {
            return new ResponseEntity<>(new ReservationRequestDTO(request.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
