package com.bookingapp.controllers;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.ReservationRequestService;
import com.bookingapp.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/requests", name = "guest makes a reservation request")
    public ResponseEntity<Long> createReservationRequest(@RequestBody ReservationRequestDTO requestDTO) {
        ReservationRequest request = new ReservationRequest(requestDTO);
        requestService.createRequest(request);
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


    @PutMapping(value="/requests/{Id}",name="updates the status of the request")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@PathVariable Long requestId/* @RequestBody RequestDTO updatedRequest*/) {
        return new ResponseEntity<>(new ReservationRequestDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/requests/{id}")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable Long id) {
        Optional<ReservationRequest> reservationOptional = requestService.findById(id);

        if (reservationOptional.isPresent()) {
            ReservationRequest reservation = reservationOptional.get();

            if (reservation.getRequestStatus().equals(RequestStatus.SENT)) {

                requestService.delete(reservation);
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else {
               //TODO Finsih the logic for ACCEPTED reservation
                return new ResponseEntity<>("Deletion not allowed for reservations with status other than SENT", HttpStatus.BAD_REQUEST);
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
