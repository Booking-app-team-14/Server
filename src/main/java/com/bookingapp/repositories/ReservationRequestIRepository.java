package com.bookingapp.repositories;

import com.bookingapp.entities.Accommodation;
import com.bookingapp.entities.ReservationRequest;
import com.bookingapp.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<ReservationRequest> findAllByAccommodationId(Long accommodationId);

    @Query("SELECT r FROM ReservationRequest r " +
            "WHERE r.accommodationId = :accommodationId " +
            "AND r.endDate BETWEEN :startDate AND :endDate")
    List<ReservationRequest> findAllByAccommodationAndEndDateBetween(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    boolean existsByAccommodationIdAndRequestStatusAndEndDateBefore(
            Long accommodationId, RequestStatus requestStatus, LocalDate endDate);

    Optional<ReservationRequest> findFirstByAccommodationIdAndUserIdAndRequestStatusOrderByEndDateDesc(
            Long accommodationId, Long userId, RequestStatus requestStatus);

    @Query("SELECT COUNT(r) > 0 FROM ReservationRequest r " +
            "WHERE r.userId = :guestId " +
            "AND r.accommodationId IN (SELECT DISTINCT a.id FROM Accommodation a WHERE a.owner.Id = :ownerId) " +
            "AND r.requestStatus = 'ACCEPTED' " +
            "AND r.endDate < CURRENT_DATE")
    boolean hasAcceptedReservationForOwner(Long guestId, Long ownerId);

    @Query("SELECT COUNT(r) > 0 FROM ReservationRequest r " +
            "WHERE r.userId = :guestId " +
            "AND r.accommodationId IN (SELECT DISTINCT a.id FROM Accommodation a WHERE a.owner.Id = :ownerId) " +
            "AND r.requestStatus = 'ACCEPTED' " +
            "AND r.endDate < CURRENT_DATE")
    boolean hasAcceptedReservationForGuest(Long ownerId, Long guestId );

    @Query("SELECT COUNT(r) > 0 FROM ReservationRequest r " +
            "WHERE r.userId = :userId " +
            "AND r.accommodationId = :accommodationId " +
            "AND r.requestStatus = 'ACCEPTED' " +
            "AND r.endDate < CURRENT_DATE")
    boolean hasAcceptedReservation(Long userId, Long accommodationId);

    List<ReservationRequest> findAllByAccommodationIdAndUserIdAndRequestStatusOrderByEndDateDesc(Long accommodationId, Long id, RequestStatus requestStatus);
}


