package com.bookingapp.services;

import com.bookingapp.dtos.*;
import com.bookingapp.entities.*;
import com.bookingapp.enums.NotificationType;
import com.bookingapp.repositories.NotificationRepository;
import com.bookingapp.repositories.UserAccountRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void sendNotification(String username) {
        simpMessagingTemplate.convertAndSend("/topic/notifications", username);
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
        List<Notification> notifications = notificationRepository.findAllNotificationsForUserById(userId);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        UserAccount userAccount = userAccountRepository.findById(userId).get();
        for (Notification notification : notifications) {

            if (userAccount.getNotWantedNotificationTypes().contains(NotificationType.RESERVATION_REQUEST_CREATED) && notification.getType().equals(NotificationType.RESERVATION_REQUEST_CREATED)){
                this.deleteNotification(notification.getId());
                continue;
            }
            else if (userAccount.getNotWantedNotificationTypes().contains(NotificationType.RESERVATION_REQUEST_CANCELLED) && notification.getType().equals(NotificationType.RESERVATION_REQUEST_CANCELLED)){
                this.deleteNotification(notification.getId());
                continue;
            }
            else if (userAccount.getNotWantedNotificationTypes().contains(NotificationType.ACCOMMODATION_REVIEWED) && notification.getType().equals(NotificationType.ACCOMMODATION_REVIEWED)){
                this.deleteNotification(notification.getId());
                continue;
            }
            else if (userAccount.getNotWantedNotificationTypes().contains(NotificationType.OWNER_REVIEWED) && notification.getType().equals(NotificationType.OWNER_REVIEWED)){
                this.deleteNotification(notification.getId());
                continue;
            }
            else if (userAccount.getNotWantedNotificationTypes().contains(NotificationType.RESERVATION_REQUEST_RESPONSE) && notification.getType().equals(NotificationType.RESERVATION_REQUEST_RESPONSE)){
                this.deleteNotification(notification.getId());
                continue;
            }

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

        List<NotificationDTO> invertedNotificationDTOS = new ArrayList<>();
        for (int i = notificationDTOS.size() - 1; i >= 0; i--) {
            invertedNotificationDTOS.add(notificationDTOS.get(i));
        }
        return invertedNotificationDTOS;
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

}
