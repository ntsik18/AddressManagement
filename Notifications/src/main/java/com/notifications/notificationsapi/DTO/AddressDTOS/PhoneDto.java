package com.notifications.notificationsapi.DTO.AddressDTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDto {

    @NotBlank
    private String phone;


}

