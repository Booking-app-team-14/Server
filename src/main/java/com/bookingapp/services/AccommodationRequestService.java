package com.bookingapp.services;

import com.bookingapp.dtos.AccommodationRequestDTO;
import com.bookingapp.entities.AccommodationRequest;
import com.bookingapp.mappers.AccommodationRequestMapper;
import com.bookingapp.repositories.AccommodationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationRequestService {

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Autowired
    private AccommodationRequestMapper modelMapper;

    public List<AccommodationRequestDTO> getAllAccommodationRequests() {
        List<AccommodationRequest> requests = accommodationRequestRepository.findAll();
        return requests.stream().map(modelMapper::toDTO).toList();
    }

    public AccommodationRequestDTO getAccommodationRequestById(Long id) {
        AccommodationRequest request = accommodationRequestRepository.findById(id).orElse(null);
        if (request == null) return null;

        return modelMapper.toDTO(request);
    }

}
