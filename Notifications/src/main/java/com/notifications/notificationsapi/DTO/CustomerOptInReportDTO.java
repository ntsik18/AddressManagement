package com.notifications.notificationsapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOptInReportDTO {
    private long totalCustomers;
    private double optedInSMS;
    private double optedInEmail;
    private double optedInPromotional;
}
