package com.bookingapp.controllers;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.services.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/accommodations")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationController {

    // TODO: make accommodationRequests service and repository
    // temporary for testing
    private List<AccommodationRequestDTO> accommodationRequests = new ArrayList<>();
    public AccommodationController(){
        this.accommodationRequests.add(new AccommodationRequestDTO(1L, "name1", "address1", "description1", "location1", "Apartment", 10.0, 15, "png", new byte[0], "ownerUsername1", "18th October 2023", "new", "Lorem ipsum 1 ...", 5, "png", new byte[0]));
        this.accommodationRequests.add(new AccommodationRequestDTO(2L, "name2", "address2", "description2", "location2", "Studio", 20.0, 20, "jpg", new byte[0], "ownerUsername2", "17th October 2023", "updated", "Lorem ipsum 2 ...", 4, "png", new byte[0]));
        this.accommodationRequests.add(new AccommodationRequestDTO(3L, "name3", "address3", "description3", "location3", "Apartment", 30.0, 25, "png", new byte[0], "ownerUsername3", "16th October 2023", "new", "Lorem ipsum 3 ...", 3, "jpg", new byte[0]));
        this.accommodationRequests.add(new AccommodationRequestDTO(4L, "name4", "address4", "description4", "location4", "Studio", 40.0, 30, "jpg", new byte[0], "ownerUsername4", "15th October 2023", "updated", "Lorem ipsum 4 ...", 2, "png", new byte[0]));
        this.accommodationRequests.add(new AccommodationRequestDTO(5L, "name5", "address5", "description5", "location5", "Apartment", 50.0, 35, "png", new byte[0], "ownerUsername5", "14th October 2023", "new", "Lorem ipsum 5 ...", 1, "jpg", new byte[0]));
        this.accommodationRequests.add(new AccommodationRequestDTO(6L, "name6", "address6", "description6", "location6", "Studio", 60.0, 40, "jpg", new byte[0], "ownerUsername6", "13th October 2023", "updated", "Lorem ipsum 6 ...", 5, "png", new byte[0]));
    }

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable Long id) {


        return new ResponseEntity<>(new AccommodationDTO(), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations(){
        List<AccommodationDTO> accDTO = new ArrayList<>();
        return new ResponseEntity<>(accDTO, HttpStatus.OK);
//        List<Accommodation> userReports = accommodationService.findAll();
//
//        List<AccommodationDTO> accDTO = new ArrayList<>();
//        for (Accommodation r : userReports) {
//            accDTO.add(new AccommodationDTO(r));
//        }
//
//        return new ResponseEntity<>(accDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/requests", name = "admin gets all the accommodation requests for creation and update")
    public ResponseEntity<List<AccommodationRequestDTO>> getAccommodationRequests() {
        return new ResponseEntity<>(this.accommodationRequests, HttpStatus.OK);
    }

    @PutMapping(value = "/requests/{id}", name = "admin approves the accommodation request")
    public ResponseEntity<AccommodationRequestDTO> approveAccommodationRequest(@PathVariable Long id) {
        for (AccommodationRequestDTO r : this.accommodationRequests) {
            if (r.getId().equals(id)) {
//                r.setRequestType("approved");
                this.accommodationRequests.remove(r);
                return new ResponseEntity<>(r, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/requests/{id}", name = "admin rejects the accommodation request")
    public ResponseEntity<Boolean> rejectAccommodationRequest(@PathVariable Long id) {
        boolean deleted = this.accommodationRequests.removeIf(r -> r.getId().equals(id));
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @GetMapping(value = "/owners/{ownerId}", name = "get all accommodations for owner")
    public ResponseEntity<List<AccommodationDTO>> getOwnerReviews(@PathVariable Long ownerId) {


        List<AccommodationDTO> accommodationsDTO = new ArrayList<>();


        return new ResponseEntity<>(accommodationsDTO, HttpStatus.OK);


    }

    /*@PostMapping(*//*, consumes = "application/json",*//* name = "owner adds an accommodation")
    public ResponseEntity<AccommodationDTO> addAccommodation() {//, @RequestBody OwnerReviewDTO ownerReviewDTO) {

        return new ResponseEntity<>(new AccommodationDTO(), HttpStatus.CREATED);


    }*/

    @PostMapping(name = "owner adds an accommodation")
    public ResponseEntity<Long> addAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation(accommodationDTO);
        accommodationService.save(accommodation);
        return new ResponseEntity<>(accommodation.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAccommodation(@PathVariable Long id) {

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    /* @PutMapping(value = "/{id}")
     public ResponseEntity<String> updateAccommodation(@PathVariable Long id) {

         return new ResponseEntity<>("Accommodation added", HttpStatus.OK);

     }*/
    @PutMapping(value = "/{id}", /*consumes = "text/plain",*/ name = "admin approves/rejects the accommodation")
    public ResponseEntity<AccommodationDTO> updateOwnerReview(@PathVariable Long id){//, @RequestBody String status) {

        return new ResponseEntity<>(new AccommodationDTO(), HttpStatus.OK);
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
            result.add(new AccommodationSearchDTO(accommodation));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
