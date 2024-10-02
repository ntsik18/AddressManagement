package com.notifications.notificationsapi.DTO.AddressDTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {

    @NotBlank
    @Email
    private String email;


}
