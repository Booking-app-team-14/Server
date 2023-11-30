package com.bookingapp.controllers;

import com.bookingapp.dtos.RequestDTO;
import com.bookingapp.entities.Request;
import com.bookingapp.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }


    @PostMapping(value = "/requests/{id}", consumes = "application/json", name = "guest makes a reservation request")
    public ResponseEntity<RequestDTO> createReservationRequest(/*@RequestBody RequestDTO request*/) {

            return new ResponseEntity<>(new RequestDTO(), HttpStatus.CREATED);
        }


    @GetMapping(value = "/requests/{id}", name = "gets a request by userId")
    public ResponseEntity<RequestDTO> getReservationRequestById(@PathVariable Long Id) {
        RequestDTO requestDTO = new RequestDTO();
        return new ResponseEntity<>(requestDTO, HttpStatus.OK);
    }


    @PutMapping(value="/requests/{Id}",name="updates the status of the request")
    public ResponseEntity<RequestDTO> updateReservationRequest(@PathVariable Long requestId/* @RequestBody RequestDTO updatedRequest*/) {
        return new ResponseEntity<>(new RequestDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/requests/{id}")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable Long id) {
      return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

    @GetMapping(value = "/users/{Id}/requests", name = "user gets reservation history")
    public ResponseEntity<List<RequestDTO>> getAllGuestHistoryReservations(@PathVariable Long Id) {
        List<Request> reservations = requestService.findAllRequestsByUsername(Id);

        List<RequestDTO> requestsDTO = new ArrayList<>();
        for (Request r : reservations) {
            requestsDTO.add(new RequestDTO(r));
        }

        return new ResponseEntity<>(requestsDTO, HttpStatus.OK);
        }
}
