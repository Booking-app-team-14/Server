package com.bookingapp.notifications.repositories;

import com.bookingapp.notifications.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationIRepository extends JpaRepository<Notification, Long> {

}
