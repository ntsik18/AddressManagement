package com.notifications.notificationsapi.BatchProcessor;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerBatchDTO {
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotNull
    private boolean optInSMS;
    @NotNull
    private boolean optInEmail;
    @NotNull
    private boolean optInPromotional;
}
