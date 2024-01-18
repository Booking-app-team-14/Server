package com.bookingapp.services;

import com.bookingapp.dtos.NotificationDTO;
import com.bookingapp.dtos.NotificationReservationCreatedDTO;
import com.bookingapp.entities.Notification;
import com.bookingapp.entities.NotificationReservationCreated;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;


    public void sendNotification(String notificationType, String username) {
        simpMessagingTemplate.convertAndSend("/notifications/" + username, notificationType);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<NotificationDTO> getAllWantedNotificationsForUser(Long userId) {
        List<Notification> notifications = notificationRepository.findAllById(List.of(userId));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getType().equals(NotificationType.RESERVATION_REQUEST_CREATED)){
//                notificationDTOS.add(new NotificationReservationCreatedDTO((NotificationReservationCreated) notification, reservationRequestService)); // TODO
            }
        }
        return notificationDTOS;
    }
}
