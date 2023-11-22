package com.bookingapp.repositories;

import com.bookingapp.entities.OwnerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerReviewRepository extends JpaRepository<OwnerReview, Long> {

    @Query("SELECT r FROM OwnerReview r WHERE r.owner.id = ?1")
    List<OwnerReview> findAllByOwnerId(Long ownerId);

    @Query("SELECT r FROM OwnerReview r WHERE r.owner.id = ?1 AND r.status = 0")
    List<OwnerReview> findAllApprovedByOwnerId(Long ownerId);

    @Query("SELECT r FROM OwnerReview r WHERE r.status = 2")
    List<OwnerReview> findAllPending();
}
