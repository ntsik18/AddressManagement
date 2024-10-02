package com.notifications.notificationsapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationReportDTO {
    private int totalNotificationsSent;
    private int successfulDeliveries;
    private int failedDeliveries;
    private int pendingNotifications;
    private double deliveryRate;
    private double failureRate;
    private double pendingRate;

}
