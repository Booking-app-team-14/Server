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

    @Query("SELECT DISTINCT a FROM Accommodation a " +
            "LEFT JOIN FETCH a.availability av " +
            "LEFT JOIN FETCH a.amenities amen " +
            "WHERE (:startDate IS NULL OR av.startDate <= :endDate) " +
            "AND (:endDate IS NULL OR av.endDate >= :startDate) " +
            "AND (:minPrice IS NULL OR :maxPrice IS NULL OR a.pricePerNight BETWEEN :minPrice AND :maxPrice) " +
            "AND (:minRating IS NULL OR a.rating >= :minRating) " +
            "AND (:minGuests IS NULL OR :maxGuests IS NULL OR " +
            "(:minGuests BETWEEN a.minNumberOfGuests AND a.maxNumberOfGuests)) " +
            "AND (:amenityIds IS NULL OR amen.id IN :amenityIds) " +
            "AND (:accommodationType IS NULL OR a.type = :accommodationType)")
    List<Accommodation> filterAccommodations(@Param("minPrice") Double minPrice,
                                             @Param("maxPrice") Double maxPrice,
                                             @Param("minRating") Double minRating,
                                             @Param("minGuests") Integer minGuests,
                                             @Param("maxGuests") Integer maxGuests,
                                             @Param("amenityIds") Set<Long> amenityIds,
                                             @Param("accommodationType") AccommodationType accommodationType,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
    @Query("SELECT a FROM Accommodation a LEFT JOIN FETCH a.location al WHERE LOWER(a.name) LIKE %:searchTerm% OR LOWER(al.city) LIKE %:searchTerm%")
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

