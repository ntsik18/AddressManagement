package com.notifications.notificationsapi.Service;

import com.notifications.notificationsapi.DTO.NotificationReportDTO;
import com.notifications.notificationsapi.Exceptions.ResourceNotFoundException;
import com.notifications.notificationsapi.Models.Customer;
import com.notifications.notificationsapi.Models.DeliveryStatus;
import com.notifications.notificationsapi.Models.NotificationStatus;
import com.notifications.notificationsapi.Repository.CustomerRepository;
import com.notifications.notificationsapi.Repository.NotificationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationReportService {

    private final NotificationStatusRepository notificationStatusRepository;
    private final CustomerRepository customerRepository;

    public NotificationReportDTO reportPerCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        List<NotificationStatus> notificationStatuses = notificationStatusRepository.findByCustomer(customer).get();
        return generateNotificationReport(notificationStatuses);
    }

    public NotificationReportDTO reportAllCustomers() {
        List<NotificationStatus> notificationStatuses = notificationStatusRepository.findAll();
        return generateNotificationReport(notificationStatuses);
    }


    private NotificationReportDTO generateNotificationReport(List<NotificationStatus> notificationStatuses) {
        int totalNotificationsSent = notificationStatuses.size();
        int successfulDeliveries = (int) notificationStatuses.stream()
                .filter(status -> status.getDeliveryStatus() == DeliveryStatus.DELIVERED)
                .count();
        int failedDeliveries = (int) notificationStatuses.stream()
                .filter(status -> status.getDeliveryStatus() == DeliveryStatus.FAILED)
                .count();
        int pendingNotifications = (int) notificationStatuses.stream()
                .filter(status -> status.getDeliveryStatus() == DeliveryStatus.PENDING)
                .count();

        double deliveryRate = totalNotificationsSent > 0 ? (double) successfulDeliveries / totalNotificationsSent * 100 : 0;
        double failureRate = totalNotificationsSent > 0 ? (double) failedDeliveries / totalNotificationsSent * 100 : 0;
        double pendingRate = totalNotificationsSent > 0 ? (double) pendingNotifications / totalNotificationsSent * 100 : 0;

        return new NotificationReportDTO(
                totalNotificationsSent,
                successfulDeliveries,
                failedDeliveries,
                pendingNotifications,
                deliveryRate,
                failureRate,
                pendingRate
        );
    }

}
