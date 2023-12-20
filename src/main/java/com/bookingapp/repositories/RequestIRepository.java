package com.bookingapp.repositories;

import com.bookingapp.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface RequestIRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.startDate <= :endDate AND r.endDate >= :startDate AND r.accommodationId = :accommodationId")
    List<Request> findRequestsBetweenDatesForAccommodationId(LocalDate startDate, LocalDate endDate, Long accommodationId);

//    Set<Request> findAllByRequestId(Long Id);
}
