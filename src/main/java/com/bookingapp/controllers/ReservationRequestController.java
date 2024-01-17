package com.bookingapp.controllers;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Owner;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Reservation;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.ReservationRequestService;
import com.bookingapp.services.ReservationService;
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

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/requests", name = "guest makes a reservation request")
    public ResponseEntity<Long> createReservationRequest(@RequestBody ReservationRequestDTO requestDTO) {
        ReservationRequest request = new ReservationRequest(requestDTO);
        requestService.createRequest(request);
        Optional<Accommodation> acc = accommodationService.getAccommodationById(request.getAccommodationId());
        if (acc.isPresent() && acc.get().isAutomatic()){
            this.approveReservationRequest(request.getId());
        }
        // TODO: send notification (of type reservationRequestNotification) to owner
        // check if receiver has that notification type enabled
        // save notification in database
        // servis.sendNotification("reservation-request-notification"); // sluzi da bi gost znao da je notifikacija stigla
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

    @PutMapping(value = "/requests/reject/{id}", name = "owner rejects a reservation request")
    public ResponseEntity<String> rejectReservationRequest(@PathVariable Long id) {
        Optional<ReservationRequest> reservationOptional = requestService.findById(id);

        if (reservationOptional.isPresent()) {
            ReservationRequest reservation = reservationOptional.get();

            reservation.setRequestStatus(RequestStatus.DECLINED);
            requestService.save(reservation);

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

            List<ReservationRequest> requests = requestService.findByAccommodationId(reservationRequest.getAccommodationId());
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
                requestService.save(r);
            }

            Reservation reservation = accommodationService.reserveAvailability(reservationRequest.getAccommodationId(), reservationRequest.getStartDate(), reservationRequest.getEndDate());
            reservationService.save(reservation);
            reservationRequest.setReservationId(reservation.getId());
            requestService.save(reservationRequest);

            Accommodation accommodation = accommodationService.findById(reservationRequest.getAccommodationId()).get();
            Owner owner = (Owner) userAccountService.findByUsername(accommodation.getOwner().getUsername());
            owner.getReservations().add(reservationRequest);
            userAccountService.save(owner);

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

                requestService.delete(reservationRequest);
                userAccountService.increaseNumberOfCancellations(reservationRequest.getUserId());
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else if (reservationRequest.getRequestStatus().equals(RequestStatus.ACCEPTED)){
                accommodationService.cancelReservation(reservationRequest);

                Accommodation accommodation = accommodationService.findById(reservationRequest.getAccommodationId()).get();
                Owner owner = (Owner) userAccountService.findByUsername(accommodation.getOwner().getUsername());
                owner.getReservations().remove(reservationRequest);
                userAccountService.save(owner);

                userAccountService.increaseNumberOfCancellations(reservationRequest.getUserId());
                requestService.delete(reservationRequest);
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
