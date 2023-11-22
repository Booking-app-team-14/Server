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
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }


    @PostMapping("/create")
    public ResponseEntity<Request> createReservationRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getReservationRequestById(@PathVariable Long requestId) {
        Request request = requestService.getRequestById(requestId);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{requestId}/update")
    public ResponseEntity<Request> updateReservationRequest(@PathVariable Long requestId, @RequestBody Request updatedRequest) {
        Request request = requestService.updateRequest(requestId, updatedRequest);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{requestId}/delete")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long requestId) {
        boolean deleted = requestService.deleteRequest(requestId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/ussers/{usersId}/requests", name = "user gets reservation history")
    public ResponseEntity<Set<GuestReservationDTO>> getOwnerReviews(@PathVariable Long userId) {
        Set<GuestReservationDTO> guestHistory = requestService.getAllGuestHistoryReservations(userId);
        return new ResponseEntity<>(guestHistory, HttpStatus.OK);
    }
}
