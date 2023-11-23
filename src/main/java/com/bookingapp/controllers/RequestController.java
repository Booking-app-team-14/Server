package com.bookingapp.controllers;

import com.bookingapp.dtos.GuestReservationDTO;
import com.bookingapp.entities.Request;
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
    public ResponseEntity<Request> createReservationRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<Request> getReservationRequestById(@PathVariable Long Id) {
        Request request = requestService.getRequestById(Id);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{requestId}")
    public ResponseEntity<Request> updateReservationRequest(@PathVariable Long requestId, @RequestBody Request updatedRequest) {
        Request request = requestService.updateRequest(requestId, updatedRequest);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long Id) {
        boolean deleted = requestService.deleteRequest(Id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/users/{Id}/requests", name = "user gets reservation history")
    public Set<GuestReservationDTO> getAllGuestHistoryReservations(Long guestId) {
        return requestService.getAllGuestHistoryReservations(guestId);
    }
}
