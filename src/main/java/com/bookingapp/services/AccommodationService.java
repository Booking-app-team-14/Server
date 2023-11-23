package com.bookingapp.services;

import com.bookingapp.dtos.OwnersAccommodationDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    // CREATE
    public Accommodation createAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
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
        Optional<Accommodation> existingAccommodation = accommodationRepository.findById(id);

        if (existingAccommodation.isPresent()) {
            updatedAccommodation.setId(id);
            return Optional.of(accommodationRepository.save(updatedAccommodation));
        } else {
            return Optional.empty();
        }
    }

    // DELETE
    public boolean deleteAccommodation(Long id) {
        if (accommodationRepository.existsById(id)) {
            accommodationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
        public Set<OwnersAccommodationDTO> getAllOwnersAccommodation(Long ownerId) {

            List<Accommodation> accommodations = getAllAccommodations();

            Set<OwnersAccommodationDTO> ownersAccommmodation = new HashSet<>();
            for (Accommodation accommodation1 : accommodations) {
                OwnersAccommodationDTO dto = new OwnersAccommodationDTO(ownerId, accommodation1.getId(),
                        accommodation1.getName(),accommodation1.getDescription());

                ownersAccommmodation.add(dto);

            }
            return ownersAccommmodation;
        }

}

