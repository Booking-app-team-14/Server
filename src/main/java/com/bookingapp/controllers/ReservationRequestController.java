package com.bookingapp.controllers;

import com.bookingapp.dtos.ReservationRequestDTO;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.services.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationRequestController {

    @Autowired
    private ReservationRequestService requestService;

    @PostMapping(value = "/requests", name = "guest makes a reservation request")
    public ResponseEntity<Long> createReservationRequest(@RequestBody ReservationRequestDTO requestDTO) {
        ReservationRequest request = new ReservationRequest(requestDTO);
        requestService.createRequest(request);
        return new ResponseEntity<>(request.getId(), HttpStatus.CREATED);
        }


    @GetMapping(value = "/requests/{id}", name = "gets a requests by Id")
    public ResponseEntity<List<ReservationRequestDTO>> getReservationRequestsByGuestId(@PathVariable Long id) {
        List<ReservationRequest> requests = requestService.findById(id);
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ReservationRequestDTO> result = requests.stream()
                .map(ReservationRequestDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @PutMapping(value="/requests/{Id}",name="updates the status of the request")
    public ResponseEntity<ReservationRequestDTO> updateReservationRequest(@PathVariable Long requestId/* @RequestBody RequestDTO updatedRequest*/) {
        return new ResponseEntity<>(new ReservationRequestDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/requests/{id}")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable Long id) {
      return new ResponseEntity<>("Deleted", HttpStatus.OK);
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
