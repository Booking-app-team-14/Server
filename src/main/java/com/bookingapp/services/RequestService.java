package com.bookingapp.services;

import com.bookingapp.dtos.GuestReservationDTO;
import com.bookingapp.dtos.RequestDTO;
import com.bookingapp.repositories.RequestIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RequestService {

    private final RequestIRepository requestRepository;

    @Autowired
    public RequestService(RequestIRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public RequestDTO createRequest(RequestDTO request) {
        return requestRepository.save(request);
    }

    public RequestDTO getRequestById(Long requestId) {
        Optional<RequestDTO> requestOptional = requestRepository.findById(requestId);
        return requestOptional.orElse(null);
    }

    public RequestDTO updateRequest(Long requestId, RequestDTO updatedRequest) {
        Optional<RequestDTO> requestOptional = requestRepository.findById(requestId);
        if (requestOptional.isPresent()) {
            RequestDTO existingRequest = requestOptional.get();
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

    public Set<GuestReservationDTO> getAllGuestHistoryReservations(Long guestId) {

        Set<RequestDTO> reservations = requestRepository.findAllById(guestId);

        Set<GuestReservationDTO> historyReservations = new HashSet<>();
        for (RequestDTO reservation : reservations) {
            GuestReservationDTO dto = new GuestReservationDTO(reservation.getUserId(),
                    reservation.getUserId(), reservation.getStartDate(), reservation.getEndDate());

            historyReservations.add(dto);

        }
        return historyReservations;
    }
}
