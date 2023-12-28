package com.bookingapp.controllers;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.AccommodationRequest;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.services.AccommodationRequestService;
import com.bookingapp.services.AccommodationService;
import com.bookingapp.services.AmenityService;
import com.bookingapp.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
    @RequestMapping("/api/accommodations")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationController {

    @Autowired
    private AccommodationRequestService accommodationRequestService;

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDTO> getAccommodationById(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccommodationDTO result = new AccommodationDTO(accommodation.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/requests", name = "admin gets all the accommodation requests for creation and update")
    public ResponseEntity<List<AccommodationRequestDTO>> getAccommodationRequests() {
        return new ResponseEntity<List<AccommodationRequestDTO>>(accommodationRequestService.getAllAccommodationRequests(), HttpStatus.OK);
    }

    @PutMapping(value = "/requests/{id}", name = "admin approves the accommodation request")
    public ResponseEntity<AccommodationRequestDTO> approveAccommodationRequest(@PathVariable Long id) {
        List<AccommodationRequestDTO> accommodationRequests = accommodationRequestService.getAllAccommodationRequests();
        return accommodationRequestService.adminApprove(accommodationRequests, id);
    }

    @DeleteMapping(value = "/requests/{id}", name = "admin rejects the accommodation request")
    public ResponseEntity<Boolean> rejectAccommodationRequest(@PathVariable Long id) {
        List<AccommodationRequestDTO> accommodationRequests = accommodationRequestService.getAllAccommodationRequests();
        return accommodationRequestService.adminReject(accommodationRequests, id);
    }

    @PostMapping(value = "/create", name = "owner adds an accommodation")
    public ResponseEntity<Long> addAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationService.save(accommodationDTO);

        accommodationRequestService.saveRequestFromAccommodation(accommodation);
        return new ResponseEntity<>(accommodation.getId(), HttpStatus.CREATED);
    }


    //upload slike za accommodation
    @PostMapping(value = "/{id}/image", consumes = "text/plain", name = "owner uploads accommodation image for his accommodation")
    public ResponseEntity<Long> uploadAccommodationImage(@PathVariable Long id, @RequestBody String imageBytes) {
        boolean ok = accommodationService.uploadAccommodationImage(id, imageBytes);
        if (!ok) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @PutMapping(value = "/update", name = "owner updates an accommodation")
    public ResponseEntity<Long> updateAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation(accommodationDTO);
        accommodation.setApproved(false);
        accommodationService.save(accommodation);

        accommodationRequestService.saveUpdateRequestFromAccommodation(accommodation);
        return new ResponseEntity<>(accommodation.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{ownerId}/accommodation", name = "gets all the accommodation of the owner")
    public ResponseEntity<List<OwnersAccommodationDTO>> getAllOwnersAccommodation(Long ownerId){
        List<OwnersAccommodationDTO> ownersAccommodationDTO = new ArrayList<>();
        return new ResponseEntity<>(ownersAccommodationDTO, HttpStatus.OK);
    }
    @GetMapping(value ="/accommodations", name = "gets the best offers")
    public ResponseEntity<List<BestOffersDTO>> getBestOffers(){
        List<BestOffersDTO> bestOffersDTO = new ArrayList<>();
        return new ResponseEntity<>(bestOffersDTO, HttpStatus.OK);
    }

    @GetMapping(value="/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(
            @RequestParam("searchTerm") String searchTerm) {
        List<Accommodation> accommodations = accommodationService.searchAccommodations(searchTerm);
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value="/filter")
    public ResponseEntity<List<AccommodationSearchDTO>> filterAccommodations(
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "minGuests", required = false) Integer minGuests,
            @RequestParam(value = "maxGuests", required = false) Integer maxGuests,
            @RequestParam(value = "amenityIds", required = false) Set<Long> amenityIds,
            @RequestParam(value = "accommodationType", required = false) AccommodationType accommodationType,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Accommodation> accommodations = accommodationService.filterAccommodations(
                minPrice, maxPrice, minRating, minGuests, maxGuests, amenityIds,
                accommodationType, startDate, endDate);

        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/sort/price/asc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByPriceAsc() {
        List<Accommodation> accommodations = accommodationService.findAllByPricePerNightAsc();
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/sort/price/desc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByPriceDesc() {
        List<Accommodation> accommodations = accommodationService.findAllByPricePerNightDesc();
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/sort/rating/desc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByRatingDesc() {
        List<Accommodation> accommodations = accommodationService.findAllByRatingDesc();
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/sort/rating/asc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByRatingAsc() {
        List<Accommodation> accommodations = accommodationService.findAllByRatingAsc();
        if (accommodations == null)
            return null;

        List<AccommodationSearchDTO> result = new ArrayList<>();

        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodations/{Id}/image")
    public ResponseEntity<String> getAccommodationImage(@PathVariable Long Id) {
        String imageBytes = accommodationService.getAccommodationImage(Id);
        if (imageBytes == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(imageBytes, HttpStatus.OK);
    }
}
