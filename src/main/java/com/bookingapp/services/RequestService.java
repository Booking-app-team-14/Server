package com.bookingapp.services;

import com.bookingapp.dtos.GuestReservationDTO;
import com.bookingapp.entities.Request;
import com.bookingapp.repositories.RequestIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RequestService {

    private final RequestIRepository requestRepository;

    @Autowired
    public RequestService(RequestIRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request getRequestById(Long requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        return requestOptional.orElse(null);
    }

    public Request updateRequest(Long requestId, Request updatedRequest) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isPresent()) {
            Request existingRequest = requestOptional.get();
            return requestRepository.save(existingRequest);
        }
        return null;
    }

    public boolean deleteRequest(Long requestId) {

        if (requestRepository.existsById(requestId)) {
            requestRepository.deleteById(requestId);
            return true;
        }
        return false;
    }

    public Request getGuestHistory(Long userId) {
        Optional<Request> requestOptional = requestRepository.findById(userId);
        if (requestOptional.isPresent()) {
            Request existingRequest = requestOptional.get();
            return  requestOptional.orElse(null);
        }
        return null;
    }
    public List<Request> findAllRequestsByUsername(Long guestId) {

        List<Request> reservations = (List<Request>) requestRepository.findAllByRequestId(guestId);

        List<GuestReservationDTO> historyReservations = new ArrayList<>();
        for (Request reservation : reservations) {
            GuestReservationDTO dto = new GuestReservationDTO(reservation.getUserId(), reservation.getUserId(),
                     reservation.getStartDate(), reservation.getEndDate());

            historyReservations.add(dto);

        }
        return null;
    }

}
