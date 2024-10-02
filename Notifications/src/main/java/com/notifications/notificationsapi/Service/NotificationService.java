package com.notifications.notificationsapi.Service;

import com.notifications.notificationsapi.DTO.NotificationStatusDTO;
import com.notifications.notificationsapi.DTO.NotificationStatusResponse;
import com.notifications.notificationsapi.Exceptions.ResourceNotFoundException;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Models.NotificationStatus;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import com.notifications.notificationsapi.Repository.NotificationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final CustomerRepository customerRepository;
    private final NotificationStatusRepository notificationStatusRepository;

    @Transactional
    public NotificationStatusResponse createNotificationStatus(Long customerId, NotificationStatusDTO notificationStatusDto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        NotificationStatus notificationStatus = getNotificationStatus(notificationStatusDto);
        notificationStatus.setCustomer(customer);
        notificationStatusRepository.save(notificationStatus);
        return getStatusResponse(notificationStatus);
    }


    @Transactional
    public NotificationStatusResponse updateNotificationStatus(Long notificationId, NotificationStatusDTO notificationStatusDto) {


        NotificationStatus notificationStatus = notificationStatusRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notificationStatus.setDeliveryStatus(notificationStatusDto.getDeliveryStatus());
        notificationStatus.setUpdatedAt(LocalDateTime.now());
        notificationStatusRepository.save(notificationStatus); // Update the status
        return getStatusResponse(notificationStatus);
    }

    private NotificationStatus getNotificationStatus(NotificationStatusDTO notificationStatusDto) {
        NotificationStatus notificationStatus = NotificationStatus.builder()
                .deliveryStatus(notificationStatusDto.getDeliveryStatus())
                .updatedAt(LocalDateTime.now())
                .notificationServiceId(notificationStatusDto.getNotificationServiceId())
                .build();
        return notificationStatus;
    }

    private NotificationStatusResponse getStatusResponse(NotificationStatus notificationStatus) {
        return NotificationStatusResponse.builder()
                .notificationServiceId(notificationStatus.getId())
                .customerId(notificationStatus.getCustomer().getId())
                .deliveryStatus(notificationStatus.getDeliveryStatus())
                .build();
    }


}
