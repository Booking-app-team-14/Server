package com.bookingapp.services;

import com.bookingapp.dtos.BestOffersDTO;
import com.bookingapp.dtos.OwnersAccommodationDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
       this.accommodationRepository = accommodationRepository;
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    // CREATE
    public Accommodation createAccommodation(Accommodation accommodation) {
        return null;
//        return accommodationRepository.save(accommodation);
    }

    // READ
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public Optional<Accommodation> getAccommodationById(Long id) {
       return accommodationRepository.findById(id);
    }

    // UPDATE
    public Optional<Accommodation> updateAccommodation(Long id, Accommodation updatedAccommodation) {
//        Optional<Accommodation> existingAccommodation = accommodationRepository.findById(id);
//
//        if (existingAccommodation.isPresent()) {
//            updatedAccommodation.setId(id);
//            return Optional.of(accommodationRepository.save(updatedAccommodation));
//        } else {
//            return Optional.empty();
//        }
        return Optional.empty();
    }

    // DELETE
    public boolean deleteAccommodation(Long id) {
//        if (accommodationRepository.existsById(id)) {
//            accommodationRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }
    public Set<OwnersAccommodationDTO> getAllOwnersAccommodation(Long ownerId) {
        return null;
//            return accommodationRepository.getOwnersAccommodations(ownerId);
    }

    public Set<BestOffersDTO> getBestOffers() {
        return null;
//        return accommodationRepository.getBestOffers();
    }

    public void save(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }
    public List<Accommodation> filterAccommodations(Double minPrice, Double maxPrice, Double minRating,
                                                    Integer minGuests, Integer maxGuests,
                                                    Set<Long> amenityIds,
                                                    AccommodationType accommodationType,
                                                    LocalDate startDate, LocalDate endDate) {


        return accommodationRepository.filterAccommodations(minPrice, maxPrice, minRating, minGuests, maxGuests,
                amenityIds, accommodationType, startDate, endDate);

    }

    public List<Accommodation> searchAccommodations(String searchTerm) {
        return accommodationRepository.findAccommodationsByNameOrLocation(searchTerm);
    }


    public List<Accommodation> findAllByPricePerNightAsc () {
        return accommodationRepository.findAllByPricePerNightAsc();
    }

    public List<Accommodation> findAllByPricePerNightDesc () {
        return accommodationRepository.findAllByPricePerNightDesc();
    }

    public List<Accommodation> findAllByRatingDesc () {
        return accommodationRepository.findAllByRatingDesc();
    }

    public List<Accommodation> findAllByRatingAsc () {
        return accommodationRepository.findAllByRatingAsc();
    }


    public Optional<Accommodation> findById(Long id) {
        return accommodationRepository.findById(id);
    }

}

