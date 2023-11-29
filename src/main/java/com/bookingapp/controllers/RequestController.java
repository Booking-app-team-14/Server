package com.bookingapp.controllers;

import com.bookingapp.dtos.GuestReservationDTO;
import com.bookingapp.dtos.RequestDTO;
import com.bookingapp.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }


    @PostMapping(value = "/{id}", consumes = "application/json", name = "guest makes a request")
    public ResponseEntity<RequestDTO> createReservationRequest(@RequestBody RequestDTO request) {
        RequestDTO createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<RequestDTO> getReservationRequestById(@PathVariable Long Id) {
        RequestDTO request = requestService.getRequestById(Id);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{requestId}")
    public ResponseEntity<RequestDTO> updateReservationRequest(@PathVariable Long requestId, @RequestBody RequestDTO updatedRequest) {
        RequestDTO request = requestService.updateRequest(requestId, updatedRequest);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        boolean deleted = requestService.deleteRequest(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/users/{Id}/requests", name = "user gets reservation history")
    public Set<GuestReservationDTO> getAllGuestHistoryReservations(@PathVariable Long Id) {
        return requestService.getAllGuestHistoryReservations(Id);
    }
}
