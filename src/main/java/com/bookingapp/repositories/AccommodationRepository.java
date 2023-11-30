package com.bookingapp.repositories;

import com.bookingapp.dtos.BestOffersDTO;
import com.bookingapp.dtos.OwnersAccommodationDTO;
import com.bookingapp.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
//    Set<OwnersAccommodationDTO> getOwnersAccommodations(Long ownerId);

//    Set<BestOffersDTO> getBestOffers();

    //List<Accommodation> findByName(String name);
}

