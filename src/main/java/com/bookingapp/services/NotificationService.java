package com.bookingapp.services;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
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
        simpMessagingTemplate.convertAndSend("/notifications", notificationType + " " + username);
    }

    public void saveAccommodationsReviewed(NotificationAccommodationReviewed notification) {
        notificationRepository.save(notification);
    }

    public void saveReservationRequestCreated(NotificationReservationCreated notification) {
        notificationRepository.save(notification);
    }

    public void saveReservationRequestCancelled(NotificationReservationCancelled notification) {
        notificationRepository.save(notification);
    }

    public void saveRequestResponse(NotificationReservationRequestResponse notification) {
        notificationRepository.save(notification);
    }

    public void saveOwnerReviewed(NotificationOwnerReviewed notification) {
        notificationRepository.save(notification);
    }

    public List<NotificationDTO> getAllWantedNotificationsForUser(Long userId, ReservationRequestService reservationRequestService, AccommodationReviewService accommodationReviewService, ReviewService reviewService) {
        List<Notification> notifications = notificationRepository.findAllById(List.of(userId));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getType().equals(NotificationType.RESERVATION_REQUEST_CREATED)){
                notificationDTOS.add(new NotificationReservationCreatedDTO((NotificationReservationCreated) notification, reservationRequestService));
            }
            else if (notification.getType().equals(NotificationType.RESERVATION_REQUEST_CANCELLED)){
                notificationDTOS.add(new NotificationReservationCancelledDTO((NotificationReservationCancelled) notification, reservationRequestService));
            }
            else if (notification.getType().equals(NotificationType.ACCOMMODATION_REVIEWED)){
                notificationDTOS.add(new NotificationAccommodationReviewedDTO((NotificationAccommodationReviewed) notification, accommodationReviewService));
            }
            else if (notification.getType().equals(NotificationType.OWNER_REVIEWED)){
                notificationDTOS.add(new NotificationOwnerReviewedDTO((NotificationOwnerReviewed) notification, reviewService));
            }
            else if (notification.getType().equals(NotificationType.RESERVATION_REQUEST_RESPONSE)){
                notificationDTOS.add(new NotificationReservationRequestResponseDTO((NotificationReservationRequestResponse) notification, reservationRequestService));
            }

            notification.setSeen(true);
            notificationRepository.save(notification);
        }
        return notificationDTOS;
    }

}
