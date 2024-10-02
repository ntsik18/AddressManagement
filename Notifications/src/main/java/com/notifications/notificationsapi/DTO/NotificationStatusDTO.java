package com.notifications.notificationsapi.DTO;

import com.notifications.notificationsapi.Models.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationStatusDTO {

    @NotNull
    private DeliveryStatus deliveryStatus;


    @NotNull
    private Long notificationServiceId; // This will be used to reference the Notification Service notification


}