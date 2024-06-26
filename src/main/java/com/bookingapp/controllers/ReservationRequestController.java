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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
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
        System.out.println(request.toString());
        requestService.createRequest(request);
        Optional<Accommodation> acc = accommodationService.getAccommodationById(request.getAccommodationId());
        if (acc.isPresent() && acc.get().isAutomatic()){
            this.approveReservationRequest(request.getId());
        }

        requestService.sendNotificationForReservation(request, NotificationType.RESERVATION_REQUEST_CREATED);

        return new ResponseEntity<>(request.getId(), HttpStatus.CREATED);
    }


    @GetMapping(value = "/requests/guest/{id}", name = "gets a requests by guest Id")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequestsByGuestId(@PathVariable Long id) {
        List<ReservationRequest> requests = requestService.findByUserId(id);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ReservationRequestDTO> result = new ArrayList<>();

        for (ReservationRequest request: requests ){
            result.add(new ReservationRequestDTO(request, userAccountService, accommodationService));
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

        for (ReservationRequest request: requests ){
            result.add(new ReservationRequestDTO(request, userAccountService, accommodationService));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/filtered-host/{id}", name = "gets requests by owner id")
    public ResponseEntity<List<ReservationRequestDTO>> getFilteredReservationRequestsByOwnerId(
            @PathVariable Long id,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "query", required = false) String query) {

        List<ReservationRequest> requests = requestService.findByOwnerId(id);
        UserAccount user = userAccountService.getUserById(id);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Filter the requests
        if (status != null && !status.equalsIgnoreCase("all")) {
            RequestStatus requestStatus = RequestStatus.valueOf(status.toUpperCase());
            requests = requests.stream()
                    .filter(r -> r.getRequestStatus() == requestStatus && r.getRequestStatus() != RequestStatus.ACCEPTED)
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            requests = requests.stream()
                    .filter(r -> !r.getStartDate().isBefore(start) && !r.getEndDate().isAfter(end))
                    .collect(Collectors.toList());
        }

        if (query != null && !query.isEmpty()) {
            requests = requests.stream()
                    .filter(r -> userAccountService.getUserById(r.getUserId()).getUsername().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        List<ReservationRequestDTO> result = requests.stream()
                .map(request -> new ReservationRequestDTO(request, userAccountService, accommodationService))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/requests/filtered-guest/{id}", name = "gets requests by owner id")
    public ResponseEntity<List<ReservationRequestDTO>> getFilteredReservationRequestsByGuestId(
            @PathVariable Long id,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "query", required = false) String query) {

        List<ReservationRequest> requests = requestService.findByUserId(id);
        UserAccount user = userAccountService.getUserById(id);
        // Filter the requests
        if (status != null && !status.equalsIgnoreCase("all")) {
            RequestStatus requestStatus = RequestStatus.valueOf(status.toUpperCase());
            requests = requests.stream()
                    .filter(r -> r.getRequestStatus() == requestStatus && r.getRequestStatus() != RequestStatus.ACCEPTED)
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            requests = requests.stream()
                    .filter(r -> !r.getStartDate().isBefore(start) && !r.getEndDate().isAfter(end))
                    .collect(Collectors.toList());
        }

        if(Objects.equals(user.getRole().toString(), "GUEST")) {
            if (query != null && !query.isEmpty()) {
                requests = requests.stream()
                        .filter(r -> r.getUserUsername().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
            }

        }
        else{
            if (query != null && !query.isEmpty()) {
                requests = requests.stream()
                        .filter(r -> userAccountService.getUserById(r.getUserId()).getUsername().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        if (query != null && !query.isEmpty()) {
            requests = requests.stream()
                    .filter(r -> r.getUserUsername().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        List<ReservationRequestDTO> result = requests.stream()
                .map(request -> new ReservationRequestDTO(request, userAccountService, accommodationService))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/guest/id/{id}", name = "gets requests by guest id")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequestsByGuestId2(@PathVariable Long id)  {
        List<ReservationRequest> requests = requestService.findByUserId(id);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ReservationRequestDTO> result = new ArrayList<>();

        for (ReservationRequest request: requests ){
            result.add(new ReservationRequestDTO(request, userAccountService, accommodationService));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/owner/id/{id}", name = "gets requests by owner id")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequestsByOwnerId(@PathVariable Long id)  {
        List<ReservationRequest> requests = requestService.findByOwnerId(id);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ReservationRequestDTO> result = new ArrayList<>();

        for (ReservationRequest request: requests ){
            result.add(new ReservationRequestDTO(request, userAccountService, accommodationService));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value="/requests/{Id}",name="updates the status of the request")
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
    public ResponseEntity<String> approveReservationRequest(@PathVariable Long id) {
        Optional<ReservationRequest> reservationOptional = requestService.findById(id);

        if (reservationOptional.isPresent()) {
            ReservationRequest reservationRequest = reservationOptional.get();

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

            requestService.sendNotificationForReservationForGuest(reservationRequest, true);

        } else {
            return new ResponseEntity<>("Reservation request not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Reservation request approved", HttpStatus.OK);
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
            } else if (reservationRequest.getRequestStatus().equals(RequestStatus.ACCEPTED)){
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
//        List<Request> reservations = requestService.findAllRequestsByUsername(Id);
//
//        List<RequestDTO> requestsDTO = new ArrayList<>();
//        for (Request r : reservations) {
//            requestsDTO.add(new RequestDTO(r));
//        }
//
//        return new ResponseEntity<>(requestsDTO, HttpStatus.OK);
        }
/*
    public ResponseEntity<List<Request>> getRequestsByUserId(@PathVariable Long userId) {
        List<Request> requests = requestService.getRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }
    */

}
