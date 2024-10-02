package com.notifications.notificationsapi.Controller;

import com.notifications.notificationsapi.DTO.CustomerOptInReportDTO;
import com.notifications.notificationsapi.DTO.NotificationReportDTO;
import com.notifications.notificationsapi.Service.CustomerReportService;
import com.notifications.notificationsapi.Service.NotificationReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final NotificationReportService notificationReportService;
    private final CustomerReportService customerReportService;

    @GetMapping("/notification")
    public ResponseEntity<NotificationReportDTO> getNotificationReport() {
        return ResponseEntity.ok(notificationReportService.reportAllCustomers());
    }

    @GetMapping("/notification/{customerId}")
    public ResponseEntity<NotificationReportDTO> getNotificationReportPerCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(notificationReportService.reportPerCustomer(customerId));
    }

    @GetMapping("/customer-opt-in")
    public ResponseEntity<CustomerOptInReportDTO> getCustomerOptInReport() {
        CustomerOptInReportDTO report = customerReportService.generateCustomerOptInReport();
        return ResponseEntity.ok(report);
    }

}
