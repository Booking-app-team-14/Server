package com.bookingapp.controllers;

import com.bookingapp.dtos.AccommodationDTO;
import com.bookingapp.dtos.BestOffersDTO;
import com.bookingapp.dtos.OwnersAccommodationDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.services.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accommodations")

public class AccommodationController {


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

    @GetMapping(value = "/owners/{ownerId}", name = "get all accommodations for owner")
    public ResponseEntity<List<AccommodationDTO>> getOwnerReviews(@PathVariable Long ownerId) {


        List<AccommodationDTO> accommodationsDTO = new ArrayList<>();


        return new ResponseEntity<>(accommodationsDTO, HttpStatus.OK);


    }

    @PostMapping(/*, consumes = "application/json",*/ name = "owner adds an accommodation")
    public ResponseEntity<AccommodationDTO> addAccommodation() {//, @RequestBody OwnerReviewDTO ownerReviewDTO) {

        return new ResponseEntity<>(new AccommodationDTO(), HttpStatus.CREATED);


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




}
