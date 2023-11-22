package com.bookingapp.reservations.services;

import com.bookingapp.reservations.models.Request;
import com.bookingapp.reservations.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
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

    // Other methods for additional operations related to requests
}
