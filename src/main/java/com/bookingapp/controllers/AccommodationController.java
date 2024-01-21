package com.bookingapp.controllers;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.repositories.LocationRepository;
import com.bookingapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
    @RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationController {

    @Autowired
    private AccommodationRequestService accommodationRequestService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private AmenityService amenityService;

    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping(value = "accommodations/{id}")
    public ResponseEntity<AccommodationDTO> getAccommodationById(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accommodationService.removePastAvailabilities(accommodation.get());

        AccommodationDTO result = new AccommodationDTO(accommodation.get(),accommodationService);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "accommodations/requests", name = "admin gets all the accommodation requests for creation and update")
    public ResponseEntity<List<AccommodationRequestDTO>> getAccommodationRequests() {
        return new ResponseEntity<List<AccommodationRequestDTO>>(accommodationRequestService.getAllAccommodationRequests(), HttpStatus.OK);
    }

    @PutMapping(value = "accommodations/requests/{id}", name = "admin approves the accommodation request")
    public ResponseEntity<AccommodationRequestDTO> approveAccommodationRequest(@PathVariable Long id) {
        List<AccommodationRequestDTO> accommodationRequests = accommodationRequestService.getAllAccommodationRequests();
        return accommodationRequestService.adminApprove(accommodationRequests, id);
    }

    @DeleteMapping(value = "accommodations/requests/{id}", name = "admin rejects the accommodation request")
    public ResponseEntity<Boolean> rejectAccommodationRequest(@PathVariable Long id) {
        List<AccommodationRequestDTO> accommodationRequests = accommodationRequestService.getAllAccommodationRequests();
        return accommodationRequestService.adminReject(accommodationRequests, id);
    }

    @PostMapping(value = "accommodations/create", name = "owner adds an accommodation")
    public ResponseEntity<Long> addAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationService.save(accommodationDTO);
        AccommodationRequest request = accommodationRequestService.saveRequestFromAccommodation(accommodation);
        return new ResponseEntity<>(accommodation.getId(), HttpStatus.CREATED);
    }


    //upload slike za accommodation
    /*@PostMapping(value = "accommodations/{id}/image", consumes = "text/plain", name = "owner uploads accommodation image for his accommodation")
    public ResponseEntity<Long> uploadAccommodationImage(@PathVariable Long id, @RequestBody String imageBytes) {
        boolean ok = accommodationService.uploadAccommodationImage(id, imageBytes);
        if (!ok) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }*/

    @PostMapping(value = "accommodations/{id}/image", consumes = "application/json", name = "owner uploads accommodation images for his accommodation")
    public ResponseEntity<Long> uploadAccommodationImages(@PathVariable Long id, @RequestBody List<String> imageBytesList) {
        boolean ok = accommodationService.uploadAccommodationImages(id, imageBytesList);
        if (!ok) {
            return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    /*@PostMapping(value = "/{id}/image", consumes = "application/json", name = "owner uploads accommodation images for his accommodation")
    public ResponseEntity<Long> uploadAccommodationImages(@PathVariable Long id, @RequestBody List<String> imageBytesList) {
        for (String imageBytes : imageBytesList) {
            boolean uploaded = accommodationService.uploadAccommodationImage(id, imageBytes);

            if (!uploaded) {
                return new ResponseEntity<>((long) -1, HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }*/

    @GetMapping(value = "accommodations/update/{accommodationId}", name = "owner gets accommodation data for update")
    public ResponseEntity<AccommodationUpdateDTO> getAccommodationUpdateData(@PathVariable Long accommodationId) {
        Optional<Accommodation> accommodationOpt = accommodationService.findById(accommodationId);
        if (accommodationOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation accommodation = accommodationOpt.get();
        AccommodationUpdateDTO accommodationUpdateDTO = new AccommodationUpdateDTO();

        accommodationUpdateDTO = accommodationService.fillAccommodationUpdateDTO(accommodation, accommodationUpdateDTO);

        return new ResponseEntity<>(accommodationUpdateDTO, HttpStatus.OK);
    }

    @PutMapping(value = "accommodations/update", name = "owner updates an accommodation")
    public ResponseEntity<Long> updateAccommodation(@RequestBody AccommodationUpdateDTO accommodationUpdateDTO) {
        Optional<Accommodation> accommodationOpt = accommodationService.findById(accommodationUpdateDTO.getId());
        if (accommodationOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation accommodation = accommodationOpt.get();
        accommodation.setApproved(false);
        accommodationService.save(accommodation);
//        accommodation = accommodationService.update(accommodation, accommodationUpdateDTO);

        accommodationRequestService.saveUpdateRequestFromAccommodation(accommodation, accommodationUpdateDTO);
        return new ResponseEntity<>(accommodation.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "accommodations/owners/{ownerId}", name = "gets all the accommodation of the owner")
    public ResponseEntity<List<OwnersAccommodationDTO>> getAllOwnersAccommodation(@PathVariable Long ownerId){
        List<OwnersAccommodationDTO> ownersAccommodationDTO = accommodationService.getAllOwnersAccommodations(ownerId);
        return new ResponseEntity<>(ownersAccommodationDTO, HttpStatus.OK);
    }

//    @GetMapping(value ="/best", name = "gets the best offers")
//    public ResponseEntity<List<BestOffersDTO>> getBestOffers(){
//        List<BestOffersDTO> bestOffersDTO = new ArrayList<>();
//        return new ResponseEntity<>(bestOffersDTO, HttpStatus.OK);
//    }

    @GetMapping(value="accommodations/search")
    public ResponseEntity<List<AccommodationSearchDTO>> searchAccommodations(
            @RequestParam("searchTerm") String searchTerm) {
        List<Accommodation> accommodations = accommodationService.searchAccommodations(searchTerm);
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation, accommodationService));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value="accommodations/filter")
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
                result.add(new AccommodationSearchDTO(accommodation, accommodationService));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("accommodations/sort/price/asc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByPriceAsc() {
        List<Accommodation> accommodations = accommodationService.findAllByPricePerNightAsc();
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation, accommodationService));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("accommodations/sort/price/desc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByPriceDesc() {
        List<Accommodation> accommodations = accommodationService.findAllByPricePerNightDesc();
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation, accommodationService));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("accommodations/sort/rating/desc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByRatingDesc() {
        List<Accommodation> accommodations = accommodationService.findAllByRatingDesc();
        if (accommodations == null)
            return null;
        List<AccommodationSearchDTO> result = new ArrayList<>();
        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation, accommodationService));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("accommodations/sort/rating/asc")
    public ResponseEntity<List<AccommodationSearchDTO>> getAllAccommodationsSortedByRatingAsc() {
        List<Accommodation> accommodations = accommodationService.findAllByRatingAsc();
        if (accommodations == null)
            return null;

        List<AccommodationSearchDTO> result = new ArrayList<>();

        for (Accommodation accommodation: accommodations)
            if (accommodation.isApproved())
                result.add(new AccommodationSearchDTO(accommodation, accommodationService));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "accommodations/accommodations/{Id}/image")
    public ResponseEntity<String> getAccommodationImage(@PathVariable Long Id) {
        String imageBytes = accommodationService.getAccommodationImage(Id);
        if (imageBytes == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(imageBytes, HttpStatus.OK);
    }

    @GetMapping(value = "accommodations/{accommodationId}/owner", name = "gets the owner of the accommodation")
    public ResponseEntity<OwnerDTO> getOwnerByAccommodationId(@PathVariable Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(accommodationId);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Owner owner = (Owner) accommodation.get().getOwner();
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OwnerDTO ownerDTO = new OwnerDTO(owner);
        return new ResponseEntity<>(ownerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{ownerId}", name = "gets the owner by owner Id")
    public ResponseEntity<OwnerDTO> getOwnerByOwnerId(@PathVariable Long ownerId) throws AccessDeniedException {
        Optional<Owner> owner = Optional.ofNullable((Owner) userAccountService.findById(ownerId));
        if (owner.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OwnerDTO ownerDTO = new OwnerDTO(owner.get());
        return new ResponseEntity<>(ownerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodations/{accommodationId}/rating", name = "gets the rating of the accommodation")
    public ResponseEntity<Double> getAccommodationRating(@PathVariable Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(accommodationId);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Double rating = accommodation.get().getRating();
        if (rating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rating, HttpStatus.OK);
    }


}
