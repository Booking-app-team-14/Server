package com.bookingapp.services;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
import com.bookingapp.enums.AccommodationType;
import com.bookingapp.repositories.AccommodationRepository;
import com.bookingapp.repositories.ImagesRepository;
import com.bookingapp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AmenityService amenityService;
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
       this.accommodationRepository = accommodationRepository;
    }

    public List<Accommodation> findAll() {
        return null;
//        return accommodationRepository.findAll();
    }

    // CREATE
    public Accommodation createAccommodation(Accommodation accommodation) {
        return null;
//        return accommodationRepository.save(accommodation);
    }

    // READ
    public List<Accommodation> getAllAccommodations() {
        return null;
//        return accommodationRepository.findAll();
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

    public Accommodation save(AccommodationDTO accommodationDTO) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserAccount user = (UserAccount) authentication.getPrincipal();


        Accommodation accommodation = new Accommodation();
        accommodation.setName(accommodationDTO.getName());
        accommodation.setDescription(accommodationDTO.getDescription());

        Location location = new Location(accommodationDTO.getLocation());
        locationRepository.save(location);
        accommodation.setLocation(location);
        accommodation.setType(accommodationDTO.getType());

        Set<Long> amenityIds = accommodationDTO.getAmenities().stream()
                .map(AmenityDTO::getId)
                .collect(Collectors.toSet());

        accommodation.setAmenities(new HashSet<>(amenityService.findAllById(amenityIds)));

        Set<Availability> availabilities = new HashSet<>();
        for(AvailabilityDTO availabilityDTO : accommodationDTO.getAvailability()){
            Availability availability = new Availability(availabilityDTO);
            availability.setAccommodation(accommodation);
            availabilities.add(availability);
        }

        accommodation.setAvailability(availabilities);

        accommodation.setImages(accommodationDTO.getImages());
        accommodation.setRating(accommodationDTO.getRating());
        accommodation.setMinNumberOfGuests(accommodationDTO.getMinNumberOfGuests());
        accommodation.setMaxNumberOfGuests(accommodationDTO.getMaxNumberOfGuests());
        accommodation.setPricePerGuest(accommodationDTO.isPricePerGuest());
        accommodation.setPricePerNight(accommodationDTO.getPricePerNight());
        accommodation.setCancellationDeadline(accommodationDTO.getCancellationDeadline());
        accommodation.setApproved(false);
        accommodation.setOwner(user);

        accommodationRepository.save(accommodation);
        return accommodation;
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


    public List<Accommodation> searchAccommodations (String searchTerm){
        return accommodationRepository.findAccommodationsByNameOrLocation(searchTerm);
    }

    public boolean uploadAccommodationImage(Long id, String imageBytes) {
        String imageType = null;
        try {
            imageType = imagesRepository.getImageType(imageBytes);
        } catch (IOException e) {
            return false;
        }

        deleteAccommodationImage(id);

        String relativePath = String.format("userAvatars\\user-%d", id);
        relativePath += "." + imageType;
        try {
            imagesRepository.addImage(imageBytes, imageType, relativePath);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteAccommodationImage(Long id) {
        String relativePath = findAccommodationImageName(id);
        if (relativePath == null) {
            return false;
        }
        return imagesRepository.deleteImage(relativePath);
    }

    private String findAccommodationImageName(Long id) {
        File directory = new File("src\\main\\resources\\images\\accommodations");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (filename.startsWith("accommodation-" + id)) {
                    return "accommodations\\" + file.getName();
                }
            }
        }
        return null;
    }
}

