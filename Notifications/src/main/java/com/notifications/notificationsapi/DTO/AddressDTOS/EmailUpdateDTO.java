package com.notifications.notificationsapi.DTO.AddressDTOS;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailUpdateDTO {
    @NotBlank
    @Email
    private String existingEmail;

    @NotBlank
    @Email
    private String newEmail;
}
