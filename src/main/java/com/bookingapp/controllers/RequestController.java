package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.RequestDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.Request;
import com.bookingapp.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping(value = "/requests", name = "guest makes a reservation request")
    public ResponseEntity<Long> createReservationRequest(@RequestBody RequestDTO requestDTO) {
        Request request = new Request(requestDTO);
        requestService.createRequest(request);
        return new ResponseEntity<>(request.getId(), HttpStatus.CREATED);
        }


    @GetMapping(value = "/requests/{id}", name = "gets a request by Id")
    public ResponseEntity<RequestDTO> getReservationRequestById(@PathVariable Long id) {
        Optional<Request> request = requestService.findById(id);
        if (request.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RequestDTO result = new RequestDTO(request.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
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
        List<RequestDTO> reservations = new ArrayList<RequestDTO>();
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
}
