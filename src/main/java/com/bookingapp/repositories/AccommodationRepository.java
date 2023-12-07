package com.bookingapp.repositories;

import com.bookingapp.dtos.BestOffersDTO;
import com.bookingapp.dtos.OwnersAccommodationDTO;
import com.bookingapp.entities.Accommodation;
import com.bookingapp.enums.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
//    Set<OwnersAccommodationDTO> getOwnersAccommodations(Long ownerId);

//    Set<BestOffersDTO> getBestOffers();

    //List<Accommodation> findByName(String name);

    @Query("SELECT a FROM Accommodation a JOIN a.availability av " +
            "WHERE av.startDate <= :endDate AND av.endDate >= :startDate")
    List<Accommodation> findAccommodationsByDateRange(LocalDate startDate, LocalDate endDate);
    @Query("SELECT a FROM Accommodation a WHERE a.pricePerNight BETWEEN :minPrice AND :maxPrice")
    List<Accommodation> findAccommodationsByPriceRange(Double minPrice, Double maxPrice);

    @Query("SELECT a FROM Accommodation a WHERE a.rating >= :minRating")
    List<Accommodation> findAccommodationsByMinRating(Double minRating);

    @Query("SELECT a FROM Accommodation a " +
            "WHERE a.minNumberOfGuests <= :maxGuests AND a.maxNumberOfGuests >= :minGuests")
    List<Accommodation> findAccommodationsByGuestsRange(Integer minGuests, Integer maxGuests);

    @Query("SELECT a FROM Accommodation a JOIN a.amenities amen WHERE amen.id IN :amenityIds")
    List<Accommodation> findAccommodationsByAmmenities(@Param("amenityIds") Set<Long> amenityIds);
    @Query("SELECT a FROM Accommodation a WHERE a.type = :type")
    List<Accommodation> findAccommodationsByType(AccommodationType type);

    @Query("SELECT a FROM Accommodation a WHERE a.name LIKE %:searchTerm% OR a.location.city LIKE %:searchTerm%")
    List<Accommodation> findAccommodationsByNameOrLocation(@Param("searchTerm") String searchTerm);

    @Query("SELECT a FROM Accommodation a ORDER BY a.pricePerNight ASC")
    List<Accommodation> findAllByPricePerNightAsc();

    @Query("SELECT a FROM Accommodation a ORDER BY a.pricePerNight DESC")
    List<Accommodation> findAllByPricePerNightDesc();

    @Query("SELECT a FROM Accommodation a ORDER BY a.rating DESC")
    List<Accommodation> findAllByRatingDesc();

    @Query("SELECT a FROM Accommodation a ORDER BY a.rating ASC")
    List<Accommodation> findAllByRatingAsc();
}

