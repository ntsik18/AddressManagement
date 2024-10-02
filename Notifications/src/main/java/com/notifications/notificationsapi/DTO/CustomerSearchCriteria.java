package com.notifications.notificationsapi.DTO;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CustomerSearchCriteria {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean optInSMS;
    private Boolean optInEmail;
    private Boolean optInPromotional;

}
