package com.bookingapp.services;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.repositories.NotificationIRepository;
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

    public NotificationDTO createNotification(NotificationDTO notification) {
        return notificationRepository.save(notification);
    }

    public NotificationDTO getNotificationById(Long notificationId) {

        Optional<NotificationDTO> notificationOptional = notificationRepository.findById(notificationId);
        return notificationOptional.orElse(null);
    }

    public NotificationDTO updateNotification(Long notificationId, NotificationDTO updatedNotification) {

        Optional<NotificationDTO> notificationOptional = notificationRepository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            NotificationDTO existingNotification = notificationOptional.get();

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
