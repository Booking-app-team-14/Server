package com.bookingapp.notifications.services;
import com.bookingapp.notifications.models.Notification;
import com.bookingapp.notifications.repositories.NotificationIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationIRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationIRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification getNotificationById(Long notificationId) {

        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        return notificationOptional.orElse(null);
    }

    public Notification updateNotification(Long notificationId, Notification updatedNotification) {

        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            Notification existingNotification = notificationOptional.get();

            return notificationRepository.save(existingNotification);
        }
        return null;
    }

    public boolean deleteNotification(Long notificationId) {

        if (notificationRepository.existsById(notificationId)) {
            notificationRepository.deleteById(notificationId);
            return true;
        }
        return false;
    }


}
