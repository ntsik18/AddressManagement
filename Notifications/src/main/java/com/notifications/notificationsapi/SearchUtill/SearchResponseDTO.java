package com.notifications.notificationsapi.SearchUtill;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class SearchResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private boolean optInSMS;
    private boolean optInEmail;
    private boolean optInPromotional;

}
