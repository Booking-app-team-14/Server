package com.bookingapp.repositories;

import com.bookingapp.entities.AccommodationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRequestRepository extends JpaRepository<AccommodationRequest, Long> {

    void deleteByAccommodationId(Long accommodationId);
}
