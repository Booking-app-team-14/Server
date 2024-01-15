package com.bookingapp.repositories;

import com.bookingapp.entities.AccommodationReview;
import com.bookingapp.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {

    @Query("SELECT ar FROM AccommodationReview ar WHERE ar.accommodation.id = ?1")
    List<AccommodationReview> findAllByAccommodationId(Long id);

    @Query("SELECT ar FROM AccommodationReview ar WHERE ar.status = 0 AND ar.accommodation.id = ?1")
    List<AccommodationReview> findAllApprovedByAccommodationId(Long id);

    @Query("SELECT ar FROM AccommodationReview ar WHERE ar.status = 2")
    List<AccommodationReview> findAllPending();

    @Query("SELECT ar FROM AccommodationReview ar WHERE ar.status = 0 AND ar.accommodation.id = :accommodationId")
    List<AccommodationReview> findByStatusAndAccommodationId(Long accommodationId);

     List<AccommodationReview> findAccommodationReviewsByAccommodationId(Long accommodationId);

}
