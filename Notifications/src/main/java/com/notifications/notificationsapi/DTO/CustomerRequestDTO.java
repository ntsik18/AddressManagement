package com.notifications.notificationsapi.DTO;


import com.notifications.notificationsapi.DTO.AddressDTOS.EmailDto;
import com.notifications.notificationsapi.DTO.AddressDTOS.PhoneDto;
import com.notifications.notificationsapi.DTO.AddressDTOS.PostalDto;
import com.notifications.notificationsapi.Models.Preferences;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Preferences preferences;  // Assuming Preferences is still a simple DTO or can be embedded

    @NotNull
    @Valid
    private EmailDto email;
    @NotNull
    private PhoneDto phone;

    private PostalDto postal;


}
