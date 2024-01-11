package com.bookingapp.repositories;

import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRequestIRepository extends JpaRepository<ReservationRequest, Long> {

    @Query("SELECT r FROM ReservationRequest r WHERE r.startDate <= :endDate AND r.endDate >= :startDate AND r.accommodationId = :accommodationId AND r.requestStatus= :status")
    List<ReservationRequest> findRequestsBetweenDatesForAccommodationId(LocalDate startDate, LocalDate endDate, Long accommodationId, RequestStatus status);

    @Query("SELECT r FROM ReservationRequest r WHERE r.userId = :userId")
    List<ReservationRequest> findAllByUserId(Long userId);

    @Query("SELECT r FROM ReservationRequest r WHERE r.userUsername = :username")
    List<ReservationRequest> findByUsername(String username);


//    Set<Request> findAllByRequestId(Long Id);
}
