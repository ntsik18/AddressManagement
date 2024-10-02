package com.notifications.notificationsapi.DTO;


import com.notifications.notificationsapi.DTO.AddressDTOS.EmailDto;
import com.notifications.notificationsapi.DTO.AddressDTOS.PhoneDto;
import com.notifications.notificationsapi.DTO.AddressDTOS.PostalDto;
import com.notifications.notificationsapi.Models.Preferences;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerUpdateDTO {
    private String firstName;

    private String lastName;

    private Preferences preferences;

    @Valid
    private EmailDto email;

    private PhoneDto phone;

    private PostalDto postal;

}
