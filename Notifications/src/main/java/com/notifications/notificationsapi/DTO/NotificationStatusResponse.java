package com.notifications.notificationsapi.DTO;

import com.notifications.notificationsapi.Models.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationStatusResponse {
    @NotNull
    private Long customerId;

    @NotNull
    private DeliveryStatus deliveryStatus;

    @NotNull
    private Long notificationServiceId;
}
