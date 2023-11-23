package com.bookingapp.controllers;

import com.bookingapp.entities.Notification;
import com.bookingapp.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping(value="/{Id}", consumes = "application/json",name="guest makes a new request or review")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long Id) {
        Notification notification = notificationService.getNotificationById(Id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{Id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long Id, @RequestBody Notification updatedNotification) {
        Notification notification = notificationService.updateNotification(Id, updatedNotification);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long Id) {
        boolean deleted = notificationService.deleteNotification(Id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
