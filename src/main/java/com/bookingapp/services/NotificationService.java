package com.bookingapp.services;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.entities.Notification;
import com.bookingapp.repositories.NotificationIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

//    private final NotificationIRepository notificationRepository;
//
//    @Autowired
//    public NotificationService(NotificationIRepository notificationRepository) {
//        this.notificationRepository = notificationRepository;
//    }
//
//    public NotificationDTO createNotification(NotificationDTO notification) {
//        return null;
//    }
//
//    public NotificationDTO getNotificationById(Long notificationId) {
//
//        return null;
//    }
//
//    public NotificationDTO updateNotification(Long notificationId, NotificationDTO updatedNotification) {
//        return null;
//    }
//
//    public boolean deleteNotification(Long notificationId) {
//
//        if (notificationRepository.existsById(notificationId)) {
//            notificationRepository.deleteById(notificationId);
//            return true;
//        }
//        return false;
//    }
//
//
//    public List<Notification> findAllNotificationsByUsername(Long userId) {
//        return null;
//    }
}
