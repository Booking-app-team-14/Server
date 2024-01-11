package com.bookingapp.services;

import com.bookingapp.dtos.AccommodationRequestDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationRequest;
import com.bookingapp.entities.Owner;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.AccommodationRequestRepository;
import com.bookingapp.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccommodationRequestService {

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private AccommodationService accommodationService;

//    @Autowired
//    private AccommodationRequestMapper modelMapper;
//
//    public List<AccommodationRequestDTO> getAllAccommodationRequests() {
//        List<AccommodationRequest> requests = accommodationRequestRepository.findAll();
//        return requests.stream().map(modelMapper::toDTO).toList();
//    }
//
//    public AccommodationRequestDTO getAccommodationRequestById(Long id) {
//        AccommodationRequest request = accommodationRequestRepository.findById(id).orElse(null);
//        if (request == null) return null;
//
//        return modelMapper.toDTO(request);
//    }

    public AccommodationRequest saveRequestFromAccommodation(Accommodation accommodation) {

        AccommodationRequest accommodationRequest = new AccommodationRequest();
        accommodationRequest.setAccommodationId(accommodation.getId());
        ZoneId zoneId = ZoneId.systemDefault();
        long epochSeconds = LocalDate.now().atStartOfDay(zoneId).toEpochSecond();
        accommodationRequest.setDateRequested(String.valueOf(epochSeconds));
        accommodationRequest.setMessage("I'm posting my new accommodation, please approve! Thanks.");
        accommodationRequest.setRequestType("new");


        accommodationRequest.setName(accommodation.getName());
        accommodationRequest.setType(accommodation.getType().toString());

        accommodationRequest.setOwnerUsername(accommodation.getOwner().getUsername());

//        userAccountService.getOwners().forEach(o -> {
//            Owner owner = (Owner) o;
//            if (owner.getAccommodations().contains(accommodation)) {
//                accommodationRequest.setOwnerUsername(owner.getUsername());
//                String profilePicturePath = owner.getProfilePicturePath();
//                ImagesRepository imagesRepository = new ImagesRepository();
//                try {
//                    accommodationRequest.setOwnerProfilePictureBytes(imagesRepository.getImageBytes(profilePicturePath));
//                    accommodationRequest.setOwnerImageType(imagesRepository.getImageType(accommodationRequest.getOwnerProfilePictureBytes()));
//                } catch (Exception e) { }
//            }
//        });

            accommodationRequest.setStars(0);

            accommodationRequestRepository.save(accommodationRequest);
            return accommodationRequest;

    }


    public List<AccommodationRequestDTO> getAllAccommodationRequests() {
        List<AccommodationRequest> requests = accommodationRequestRepository.findAll();
        List<AccommodationRequestDTO> dtos = new ArrayList<>();
        for (AccommodationRequest request : requests) {
            AccommodationRequestDTO dto = new AccommodationRequestDTO(request);
            dtos.add(dto);
        }
        return dtos;
    }

    public void save(AccommodationRequestDTO accommodationRequestDTO) {
        AccommodationRequest accommodationRequest = new AccommodationRequest(accommodationRequestDTO);
        accommodationRequestRepository.save(accommodationRequest);
    }

    public void saveUpdateRequestFromAccommodation(Accommodation accommodation, String message) {
        AccommodationRequestDTO accommodationRequestDTO = new AccommodationRequestDTO(accommodation);
        Instant instant = Instant.now();
        long epochSeconds = instant.getEpochSecond();
        accommodationRequestDTO.setDateRequested(String.valueOf(epochSeconds));
        accommodationRequestDTO.setMessage("\"" + message + "\"");
        accommodationRequestDTO.setRequestType("updated");
        accommodationRequestDTO.setOwnerUsername(accommodation.getOwner().getUsername());
        ImagesRepository imagesRepository = new ImagesRepository();
        try {
            accommodationRequestDTO.setOwnerProfilePictureBytes(imagesRepository.getImageBytes(accommodation.getOwner().getProfilePicturePath()));
            accommodationRequestDTO.setOwnerImageType(imagesRepository.getImageType(accommodationRequestDTO.getOwnerProfilePictureBytes()));
            accommodationRequestDTO.setMainPictureBytes(imagesRepository.getImageBytes(accommodation.getImages().stream().findFirst().get()));
            accommodationRequestDTO.setImageType(imagesRepository.getImageType(accommodationRequestDTO.getMainPictureBytes()));
        } catch (Exception ignored) { }
        AccommodationRequest accommodationRequest = new AccommodationRequest(accommodationRequestDTO);
        accommodationRequestRepository.save(accommodationRequest);
    }

    private void deleteByAccommodationId(Long id) {
        List<AccommodationRequest> requests = accommodationRequestRepository.findAll();
        for (AccommodationRequest r : requests) {
            if (r.getAccommodationId().equals(id)) {
                accommodationRequestRepository.deleteById(r.getId());
            }
        }
    }

    public ResponseEntity<AccommodationRequestDTO> adminApprove(List<AccommodationRequestDTO> accommodationRequests, Long id) {
        for (AccommodationRequestDTO r : accommodationRequests) {
            if (r.getAccommodationId().equals(id)) {
                Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
                if (accommodation.isPresent()) {
                    Accommodation a = accommodation.get();
                    a.setApproved(true);
                    accommodationService.save(a);
                    deleteByAccommodationId(r.getAccommodationId());
                }
                return new ResponseEntity<>(r, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Boolean> adminReject(List<AccommodationRequestDTO> accommodationRequests, Long id) {
        for (AccommodationRequestDTO r : accommodationRequests) {
            if (r.getAccommodationId().equals(id)) {
                boolean deleted = accommodationService.deleteAccommodation(id);
                deleteByAccommodationId(r.getAccommodationId());
                return new ResponseEntity<>(deleted, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

}
