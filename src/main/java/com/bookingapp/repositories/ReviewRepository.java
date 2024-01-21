package com.bookingapp.repositories;

import com.bookingapp.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRecipientId(Long recipientId);

    List<Review> findByApprovedFalse();

    List<Review> findByRecipientIdAndApproved(Long recipientId, boolean approved);

    //List<Review> findByRecipientIdAndStatus(@Param("recipientId") Long recipientId, @Param("status") boolean status);

}

