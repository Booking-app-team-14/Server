package com.bookingapp.repositories;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query("SELECT n FROM Notification n WHERE n.receiver.Id = ?1")
    List<Notification> findAllNotificationsForUserById(Long userId);
}
