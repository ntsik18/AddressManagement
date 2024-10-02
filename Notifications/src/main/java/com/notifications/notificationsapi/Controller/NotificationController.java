package com.notifications.notificationsapi.Controller;

// NotificationController.java

import com.notifications.notificationsapi.DTO.NotificationStatusDTO;
import com.notifications.notificationsapi.DTO.NotificationStatusResponse;
import com.notifications.notificationsapi.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
When the external Notification Service attempts to send a notification (email, SMS, etc.),
it will report the result (status) back to Customer Notification Address Facade System.
 */

@RestController
@RequestMapping("/notificationAddressService")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/{customerId}")
    public ResponseEntity<NotificationStatusResponse> createNotificationStatus(
            @PathVariable Long customerId,
            @RequestBody NotificationStatusDTO notificationStatusDto) {

        return ResponseEntity.status(HttpStatus.CREATED).
                body(notificationService.createNotificationStatus(customerId, notificationStatusDto));
    }

    //for Notification Service api
    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationStatusResponse> updateNotificationStatus(
            @PathVariable Long notificationId,
            @RequestBody NotificationStatusDTO notificationStatusDto) {

        return ResponseEntity.ok(notificationService.updateNotificationStatus(notificationId, notificationStatusDto));
    }

}

